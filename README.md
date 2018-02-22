# java-encapsulation

## Encapsulation
- Font: https://www.adobe.com/devnet/actionscript/learning/oop-concepts/encapsulation.html

- When you create an object in an object-oriented language, you can hide the complexity of the internal workings of the object. As a developer, there are two main reasons why you would choose to hide complexity.

- The first reason is to provide a simplified and understandable way to use your object without the need to understand the complexity inside. As mentioned above, a driver doesn't need to know how an internal combustion engine works. It is sufficient to know how to start the car, how to engage the transmission if you want to move, how to provide fuel, how to stop the car, and how to turn off the engine. You know to use the key, the shifter (and possibly clutch), the gas pedal and the brake pedal to accomplish each of these operations. These basic operations form an interface for the car. Think of an interface as the collection of things you can do to the car without knowing how each of those things works.

- Hiding the complexity of the car from the user allows anyone, not just a mechanic, to drive a car. In the same way, hiding the complex functionality of your object from the user allows anyone to use it and to find ways to reuse it in the future regardless of their knowledge of the internal workings. This concept of keeping implementation details hidden from the rest of the system is key to object-oriented design.

- Take a look at the CombustionEngine class below. Notice that it has only two public methods:
```
start() and stop()
```

Those public methods can be called from outside of the object. All of the other functions are private, meaning that they are not publicly visible to the rest of the application and cannot be called from outside of the object.

```
package engine {
    public class CombustionEngine {

        public function CombustionEngine() {}

        private function engageChoke():void {}
        private function disengageChoke():void {}
        private function engageElectricSystem():void {}
        private function powerSolenoid():void {}
        private function provideFuel():void {}
        private function provideSpark():void {}

        public function start():void {
            engageChoke();
            engageElectricSystem();
            powerSolenoid();
            provideFuel();
            provideSpark();
            disengageChoke();
        }
      public function stop():void {}
    }
}

```

You would use this class as follows:

```
var carEngine:CombustionEngine = new CombustionEngine();
carEngine.start();
carEngine.stop();
```

- The second reason for hiding complexity is to manage change. Today most of us who drive use a vehicle with a gasoline-powered internal combustion engine. However, there a gas-electric hybrids, pure electric motors, and a variety of internal combustion engines that use alternative fuels. Each of those engine types has a different internal mechanism yet we are able to drive each of them because that complexity has been hidden. This means that, even though the mechanism which propels the car changes, the system itself functions the same way from the user's perspective.

- Imagine a relatively complex object that parses an audio file and yet allows you only to play, seek or stop the playback. Over time, the author of this object could change the internal algorithm of how the object works completely—new optimization for speed could be added, memory handling could be improved, additional file formats could be supported, and the like. However, since the rest of the source code in your application uses only the play, seek and stop methods, this object can change very significantly internally while the remainder of your application can stay exactly the same. This technique of providing encapsulated code is critical in team environments.

- Take a look at the refactored CombustionEngine class below. Over time, technology has improved. Instead of having a manual choke and carburetor, it now uses fuel injection. The internal system has therefore changed dramatically.

Notice that it still only has two public methods:
```
start()
and
stop()
```

The rest of your system will remain the same even though the internals of your engine changed. Imagine that you have a relatively complex application that uses the engine from hundreds of places. Not having to update each of those places when you change engines could save many days of work.
```
package engine {
    public class CombustionEngine {

        public function CombustionEngine() {}

        private function engageElectricSystem():void {}
        private function powerComputer():void {}
        private function powerSolenoid():void {}
        private function provideFuel():void {}
        private function provideSpark():void {}

        public function start():void {
            engageElectricSystem();
            powerComputer();
            powerSolenoid();
            provideFuel();
            provideSpark();
        }

        public function stop():void {}
    }
}
```

You would use this class as follows:

```
var carEngine:CombustionEngine = new CombustionEngine();
carEngine.start();
carEngine.stop();
```

### Implicit getters and setters
- ActionScript 3 has a concept of implicit getters and setters which allows you to further hide implementation details from the user when setting or reading a property. Look at the following class which represents a
Person

- This class uses the method
```
updateDisplay()
```
to refresh what is displayed on the screen. When you update the Person's first name or last name, you need to remember to call the
```
updateDisplay()
```
method to ensure the screen is updated with the Person's new name.


```
package people {
    public class Person {

        public var firstName:String;
        public var lastName:String;

        public function Person(){}

        public function updateDisplay():void {
            trace( firstName + " " + lastName );
        }
    }
}
```

You would use this class in the following way:


```
var person:Person = new Person();
person.firstName = "Lee";
person.updateDisplay(); //output: Lee null

person.lastName = "Alan";
person.updateDisplay(); //output: Lee Alan
```

- In this example, any place in your code where you update either the first or last name, you also need to remember to call updateDisplay().
Not only is it easy to forget, but if this was a more complicated object, you might cause strange situations where the object is out of sync if you update part of it, but not the rest.

- ActionScript 3 allows you to hide complexity while solving both of the above problems with implicit getters and setters. An implicit getter and setter is really two functions that look like a single property to the user of the object. One function— the getter—is called when a property is read. The other function—the setter—is called when a property is set. Together, these functions allow users of your class to access private properties as if they were accessing public properties. Additionally, the access is provided without having two public functions for each private property that provide read and write access.

- An added benefit of a setter function is that it provides a convenient place to ensure that data is formed in a way that is expected by your object. Using a setter function, an object can potentially validate the data before it is stored internally, reducing the need to further check the data each time it is used and providing some measure of consistency to the object's internal state.

- In the following code, the firstName and lastName public variables have been converted to private properties with implicit getters and setters. Now, whenever the firstName or lastName is set, the String value is checked to ensure it is not null and that it has characters before the value is assigned to the property. Then the updateDisplay() will automatically be called. There is no chance that the user can forget to call the updateDisplay() method. Further, the updateDisplay() method has been made private so that users of the object cannot call it directly. That functionality has been encapsulated because the user does not need to know how the object works internally.

```
package people {
    public class Person {

        private var _firstName:String;
        private var _lastName:String;

        public function Person(){}

        public function get lastName():String {
            return _lastName;
        }

        public function set lastName(value:String):void {
            if(value!=null && value.length>0){
                _lastName = value;
                updateDisplay();
            }
            else{
                trace("Invalid name");
            }
        }

        public function get firstName():String {
            return _firstName;
        }

        public function set firstName(value:String):void {
            if(value!=null && value.length>0){
                _firstName = value;
                updateDisplay();
            }
            else{
                trace("Invalid name");
            }
        }

        private function updateDisplay():void {
            trace( firstName + " " + lastName );
        }
    }
}
```

- As you can see above, to create a getter and setter for a property, you first create a private variable to hold the value. In this case, that private variable is named \_lastName. The underscore is a variable naming convention which indicates that \_lastName is private and cannot be seen outside of the class.

private var \_lastName:String;

Next, you create two functions. One uses the keyword get, which will be called automatically when you read the property, and one uses the keyword set which will be called automatically when the property is set. Note that the get function accepts no arguments and returns the same type as the \_lastName variable. The set function accepts a single argument of the same type as the \_lastName variable, assigns that argument to \_lastName if the argument passes the conditional test, and returns nothing.

```
public function get lastName():String {
    return _lastName;
}

public function set lastName(value:String):void {
    if(value!=null && value.length>0){
        _lastName = value;
        updateDisplay();
    }
    else{
        trace("Invalid name");
    }
}
```

You would use this class in the following way:

```
var person:Person = new Person();
person.firstName = "Lee"; //output: Lee null
person.lastName = "Alan"; //output: Lee Alan
person.firstName =;  //output: Invalid name
```

- The firstName and lastName properties still look like simple variables to the user of this class; however, you now have the ability to do additional work when either is called. In this example, the argument is checked and the updateDisplay() method is called, but now it is done implicitly instead of explicitly, removing the possibility of user error and hiding the implementation details
