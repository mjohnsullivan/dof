import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
//import 'package:fluwx/fluwx.dart';
import 'package:image/image.dart' as image;
import 'package:path_provider/path_provider.dart';

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Photo App',
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  Future<File> _imageFile;

  @override
  void initState() {
    super.initState();
    //Fluwx.registerApp(RegisterModel(appId: "wxd930ea5d5a258f4f"));
  }

  void _onImageButtonPressed(ImageSource source) {
    setState(() {
      _imageFile = ImagePicker.pickImage(source: source, maxHeight: 350.0);
    });
  }

  Widget _previewImage() {
    return FutureBuilder<File>(
        future: _imageFile,
        builder: (BuildContext context, AsyncSnapshot<File> snapshot) {
          if (snapshot.connectionState == ConnectionState.done &&
              snapshot.data != null) {
            return ApplyFilterWidget(snapshot.data);
          } else if (snapshot.error != null) {
            return const Text(
              'Error picking image.',
              textAlign: TextAlign.center,
            );
          } else {
            return Container();
          }
        });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Flutter Photo app')),
      body: Center(child: _previewImage()),
      floatingActionButton: FloatingActionButton(
        onPressed: () => _onImageButtonPressed(ImageSource.camera),
        heroTag: 'image1',
        child: const Icon(Icons.camera_alt),
      ),
    );
  }
}

class ApplyFilterWidget extends StatefulWidget {
  final File file;

  ApplyFilterWidget(this.file);

  @override
  ApplyFilterState createState() => ApplyFilterState();
}

enum FilterOptions { None, BlackAndWhite, Vintage, Vignette, Emboss }

class ApplyFilterState extends State<ApplyFilterWidget> {
  //Fluwx _fluwx;
  FilterOptions _filterState;
  @override
  void initState() {
    super.initState();
    //_fluwx = Fluwx();
    _filterState = FilterOptions.None;
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        drawImage(_filterState),
        FloatingActionButton(
          onPressed: () {
            /*_fluwx.share(WeChatShareImageModel(
                image: widget.file.uri.toString(),
                thumbnail:
                    'assets://logo.png', // this is to prevent an OOM when the plugin tries to create a thumbnail. :-P
                scene: WeChatScene.SESSION));*/
          },
          heroTag: 'image0',
          child: const Icon(Icons.share),
        ),
        Row(children: [
          RaisedButton(
            child: Text('no filter'),
            onPressed: () => drawImage(FilterOptions.None)
          ),
          RaisedButton(
            child: Text('black and white'),
            onPressed: () => drawImage(FilterOptions.BlackAndWhite)
          ),
          RaisedButton(
            child: Text('vintage'),
            onPressed: () => drawImage(FilterOptions.Vintage),
          ),
          RaisedButton(
            child: Text('vignette'),
            onPressed: () => drawImage(FilterOptions.Vignette),
          ),
          /*RaisedButton(
            child: Text('emboss'),
            onPressed: () => drawImage(FilterOptions.Emboss),
          )*/
        ])
      ],
    );
  }

  Widget drawImage(FilterOptions newFilter) {
    setState(() {
      _filterState = newFilter;
    });
    if (_filterState == FilterOptions.None) {
      return Image.file(widget.file);
    } else {
      return FilteredImage(widget.file, _filterState);
    }
  }
}

class FilteredImage extends StatelessWidget {
  final File originalFile;
  final FilterOptions filterState;
  static int foo =0;
  FilteredImage(this.originalFile, this.filterState);
  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _localPath,
      builder: (BuildContext context, AsyncSnapshot<File> snapshot) {
        if (snapshot.hasData) {
          image.Image unmodifiedImage = image.decodeImage(originalFile.readAsBytesSync());

          image.Image result = unmodifiedImage;
          switch(filterState) {
            case FilterOptions.BlackAndWhite: 
              result = image.grayscale(unmodifiedImage);
              break;
            case FilterOptions.Vintage:
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
          snapshot.data.writeAsBytesSync(image.encodePng(image.copyRotate(result, 90)));
          return Image.file(snapshot.data);
        } else {
          return Image.file(originalFile);
        }
      },
    );
  }

  Future<File> get _localPath async {
    var directory = await getApplicationDocumentsDirectory();
    return File('${directory.path}/$filterState${originalFile.uri.pathSegments.last}.jpg');
  }
}
