# Simple cli based on https://github.com/M-Media-Group/Covid-19-API

## To build an executable jar use
```commandline
./gradlew shadowJar
```

## Usage example
```commandline
java -jar build/libs/covid-cli-1.0-SNAPSHOT-all.jar

Enter the country name to get COVID cases:
Estonia
confirmed: 593571
recovered: 0
deaths: 2634
vaccinated level in % of total population: 64.45

```