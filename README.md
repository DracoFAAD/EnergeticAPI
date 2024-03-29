# Energetic API [![](https://jitpack.io/v/DracoFAAD/EnergeticAPI.svg)](https://jitpack.io/#DracoFAAD/EnergeticAPI)
Energetic API is an API that tries to bring things like FE (Electricity), Custom Fluids & Gas into paper servers. Using the API is really easy. Let me show you how to use it.

Currently supported: 1.20

## 1. Implementation
### Maven
Repository:
````xml    
<repositories>
    <repository>
       <id>jitpack.io</id>
       <url>https://jitpack.io</url>
    </repository>
</repositories>
````

Dependency:
````xml
<dependency>
    <groupId>com.github.DracoFAAD</groupId>
    <artifactId>EnergeticAPI</artifactId>
    <version>1.0</version>
</dependency>
````

### Gradle
Repository:
````gradle
maven { url 'https://jitpack.io' }
````

Dependency:
````gradle
implementation 'com.github.DracoFAAD:EnergeticAPI:1.0'
````

## 2. Getting the Energetic API Instance

````java
import me.dracofaad.energeticapi.EnergeticAPI;

// It will get the plugin and cast it to EnergeticAPI so that you can use it.
EnergeticAPI energeticAPI = EnergeticAPI.getEnergeticAPI();
````

### You should be ready to start using the plugin! If you want to learn more, look at the [wiki](https://github.com/DracoFAAD/EnergeticAPI/wiki)!
