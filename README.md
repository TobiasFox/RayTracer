# Ray tracer
<table>
    <tr>
        <td>The houses consist only of spheres with a linear interpolating gradient</td>
        <td>Complex shapes with instantiation of object groups</td>
        <td>Complex shapes, like cylinders and cones</td>
    </tr>
    <tr>
        <td><img src="./doc/images/buildings.png"></td>
        <td><img src="./doc/images/complex_forms1.png"></td>
        <td><img src="./doc/images/complex_forms2.png"></td>
    </tr>
    <tr>
        <td>Usage of point light</td>
        <td>Usage of textures</td>
        <td>360°-image of houses</td>
    </tr>
    <tr>
        <td><img src="./doc/images/pointlight2.png"></td>
        <td><img src="./doc/images/textures.png"></td>
        <td><img src="./doc/images/360_degrees_1.png"></td>
    </tr>
</table>
<br><br>
<p align="center" style="font-size:22px">
    <a href="./doc/Trailer.mp4">Trailer</a>
</p>

This is my own implementation of a ray tracer in Java as part of my bachelor studies. 

## Basic structure

The [ImageCreator](./src/raytracer/imageCreator/ImageCreator.java)-class lets you adjust a lot of options for the raytracer. You can see the possible options in the Interface [ImageCreator](./src/raytracer/imageCreator/ImageCreatorInterface.java).  
The Main-class in every instanciates an object from the ImageCreator with the adjusted options. Furthermore, the main class creates all 3D-objects (with their location, transformation, color, ...) which should be drawn in the current picture. 
<br><br>

## Run the ray tracer
After you clone the repo, compile all files and run one of the main-classes.  
If you have problems or question feel free to write me: tobiasfox@gmx.net
<br><br>

## Mlestones  
Here you can find the german documentation from every milestones of my raytracer. 

### [a01: Rote Scheibe](doc/a01/a01.md)
### [a02: Viele Scheiben](doc/a02/a02.md)
### [a03: Gestreifter Himmel](doc/a03/a03.md)
### [a04: Unbeleuchteter Planet](doc/a04/a04.md)
### [a05: Beleuchteter Planet](doc/a05/a05.md)
### [a06: Viele Dinge](doc/a06/a06.md)
### [a07: Instanziierung](doc/a07/a07.md)
### [a08: Metall und Glas](doc/a08/a08.md)
### [a09: Wolken am Himmel](doc/a09/a09.md)
### [a10: Licht und Schatten](doc/a10/a10.md)

### Extra Milestones  

### [b01: Zylinder und Kegel](doc/b01/b01.md)
### [b03: Parallelisierung](doc/b03/b03.md)
### [b04: 360-Grad-Bilder](doc/b04/b04.md)
The code for b04 is on another branch: "images360".