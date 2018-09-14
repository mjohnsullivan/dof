import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:fluwx/fluwx.dart';
import 'package:image/image.dart' as image;
import 'package:path_provider/path_provider.dart';

void main() => runApp(MaterialApp(
      theme: ThemeData(primaryColor: Colors.red),
      home: MyHomePage(),
    ));

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  StreamController<File> _imageHandler;

  @override
  void initState() {
    super.initState();
    Fluwx.registerApp(RegisterModel(appId: "wxd930ea5d5a258f4f"));
    _imageHandler = new StreamController();
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder(
        stream: _imageHandler.stream,
        builder: (BuildContext context, AsyncSnapshot<File> snapshot) {
          if (snapshot.hasData && snapshot.data != null) {
            return PhotoViewer(snapshot.data, _imageHandler);
          }
          return IntroPage(_imageHandler);
        });
  }

  @override
  void deactivate() {
    super.deactivate();
    _imageHandler.close();
  }
}

class IntroPage extends StatelessWidget {
  final StreamController<File> _imageHandler;
  IntroPage(this._imageHandler);
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Flutter照片应用')),
      body: Container(
          color: Color(0xffffffe0),
          child: Center(child: Image.asset('assets/dash.png'))),
    );
  }

  _getImage() async {
    var imageFile = await ImagePicker.pickImage(
        source: ImageSource.camera, maxHeight: 400.0);
    _imageHandler.add(imageFile);
  }
}

enum FilterOptions { None, BlackAndWhite, Sepia, Vignette, Emboss }

class PhotoViewer extends StatefulWidget {
  final File originalFile;
  final StreamController<File> _imageHandler;
  File filterFile;

  PhotoViewer(this.originalFile, this._imageHandler)
      : filterFile = originalFile;

  @override
  PhotoViewerState createState() => PhotoViewerState();
}

class PhotoViewerState extends State<PhotoViewer> {
  Fluwx _fluwx;

  FilterOptions _filterState;
  @override
  void initState() {
    super.initState();
    _fluwx = Fluwx();
    _filterState = FilterOptions.None;
  }

  _clearImage() {
    widget._imageHandler.add(null);
  }

  _shareWithWeChat() {
    _fluwx.share(WeChatShareImageModel(
        image: widget.originalFile.uri.toString(),
        thumbnail:
            'assets://logo.png', // this is to prevent an OOM when the plugin tries to create a thumbnail. :-P
        scene: WeChatScene.SESSION));
  }

  Widget _shareButton() {
    return FloatingActionButton(
      backgroundColor: Colors.red,
      child: const Icon(Icons.share),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
            leading: IconButton(
              icon: Icon(Icons.arrow_back),
              onPressed: _clearImage,
            ),
            title: Text('Customize photo')),
        floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
        body: Container(
            color: Color(0xffffffe0),
            child: Column(
              children: [
                drawImage(_filterState),
                drawFilterButtons(),
              ],
            )));
  }

  Widget drawImage(FilterOptions newFilter) {
    setState(() {
      _filterState = newFilter;
    });
    if (_filterState == FilterOptions.None) {
      return Image.file(widget.originalFile);
    } else {
      return FilteredImage(widget.originalFile,
          (File newFile) => widget.filterFile = newFile, _filterState);
    }
  }

  Widget drawFilterButtons() {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
      children: [
        FilterButton('dashSmall', onTap: () => drawImage(FilterOptions.None)),
        FilterButton('sepia', onTap: () => drawImage(FilterOptions.Sepia)),
        FilterButton('vignette',
            onTap: () => drawImage(FilterOptions.Vignette)),
        FilterButton('emboss', onTap: () => drawImage(FilterOptions.Emboss)),
      ],
    );
  }
}

class FilterButton extends StatelessWidget {
  GestureTapCallback onTap;
  String filename;
  FilterButton(this.filename, {this.onTap});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Image.asset('assets/$filename.png'),
      ),
    );
  }
}

class FilteredImage extends StatelessWidget {
  final File originalFile;
  final FilterOptions filterState;
  static int index = 0;
  Function updateFileName;

  FilteredImage(this.originalFile, this.updateFileName, this.filterState);
  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _localPath,
      builder: (BuildContext context, AsyncSnapshot<File> snapshot) {
        if (snapshot.hasData) {
          updateFileName(snapshot.data);
          image.Image unmodifiedImage =
              image.decodeImage(originalFile.readAsBytesSync());

          image.Image result = unmodifiedImage;
          switch (filterState) {
            case FilterOptions.BlackAndWhite:
              result = image.grayscale(unmodifiedImage);
              break;
            case FilterOptions.Sepia:
              result = image.sepia(unmodifiedImage);
              break;
            case FilterOptions.Vignette:
              result = image.vignette(unmodifiedImage);
              break;
            case FilterOptions.Emboss:
              result = image.emboss(unmodifiedImage);
              break;
            case FilterOptions.None:
            default:
              break;
          }
          snapshot.data
              .writeAsBytesSync(image.encodePng(image.copyRotate(result, 90)));
          return Image.file(snapshot.data);
        } else {
          return Image.file(originalFile);
        }
      },
    );
  }

  Future<File> get _localPath async {
    var directory = await getApplicationDocumentsDirectory();
    return File(
        '${directory.path}/$filterState${originalFile.uri.pathSegments.last}.jpg');
  }
}
