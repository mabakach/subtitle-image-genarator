# Subtitle image generator

Subtitle image generator is a command line application which converts a text file into a series of images. Each image contains one line from the input text file. The images can be used as overlays in videos in video editing applications that do not support subtitles natively.

## Build the application

### Prerequisites

To build the application your self you need:

- Java 11 JDK (which ever you like)
- Maven 3.6 or later

### Snapshot build

```
mvn clean install
```

### Release build

```
mvn --batch-mode release:clean
mvn --batch-mode release:prepare
mvn --batch-mode release:perform
```

## Run subtitle image generator

```
java -jar /path/to/subtitle-image-genarator-<version>.jar 
```

The following command line arguments are supported:

```
 -fn <arg>   Font name (optional, default is Arial Rounded)
 -fs <arg>   Font size in pixel (optional, default is 48)
 -h          Print this help
 -i <arg>    Path to input text file.
 -ih <arg>   Image height in pixel (optional, default is 1080)
 -iw <arg>   Image width in pixel (optional, default is 1980)
 -o <arg>    Path to output folder.
 -sh <arg>   Sub title bar height in pixel (optional, default is 170)
```

