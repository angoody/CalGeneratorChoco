# CalGeneratorBack

## Description du projet

Ce projet expose une API REST permettant la génération d'un calendar par contraintes. Ce projet a été demandé par l'ENI Ecole Informatique Nantes.

## Documentation de l'API

### Solve

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /solve | Objet Problem | Renvoi les calendars répondant au problème | 1 |

### Objet problem

| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
| periodOfTraining | Period  | Non | Non |
| numberOfCalendarToFound | Integer | Oui | Oui (5) |
| constraints | Constraint | Non |  Non |
| moduleOfTraining| List<Module> |  Non | Non | 

### Objet Period

| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
|  start  | String |Oui|Non|
|  end  | String|Oui|Non|
    
### Objet Constraint

| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
|place |ConstraintPriority de type Integer |Non|Non|
|maxDurationOfTraining |ConstraintPriority de type Integer |Non|Non|
|maxDurationOfTraining |ConstraintPriority de type Integer |Non|Non|
|trainingFrequency |ConstraintPriority de type TrainingFrequency|Non|Non|
|maxStudentInTraining |ConstraintPriority de type StudentCompany |Non|Non|
|listStudentRequired |Liste de ConstraintPriority de type Student |Non|Non|
|listPeriodeOfTrainingExclusion |Liste de ConstraintPriority de type Period |Non|Non|
|listPeriodeOfTrainingInclusion |Liste de ConstraintPriority de type Period |Non|Non|
|constraintPrerequisite |ConstraintPriority de type Boolean |Non|Non|


### Objet ConstraintPriority de type Integer
| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
| priority |Integer|Oui|Oui (-1)|
|Value |Integer |Oui|Non|

### Objet ConstraintPriority de type TrainingFrequency
| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
| priority |Integer|Oui|Oui (-1)|
|maxWeekInTraining|Integer|Oui|Non|
|minWeekInCompany|Integer|Oui|Non|
    
### Objet ConstraintPriority de type StudentCompany
| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
| priority |Integer|Oui|Oui (-1)|
|maxStudentInTraining|Integer|Oui|Non|
|listStudentCompany|Liste de Student|Oui|Non|

### Objet ConstraintPriority de type Student
| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
| priority |Integer|Oui|Oui (-1)|
|idStudent|Integer|Oui|Non|
|listClassees|Liste de Classes|Oui|Non|

### Objet Liste de ConstraintPriority de type Period 
| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
| priority |Integer|Oui|Oui (-1)|
| start  | String |Oui|Non|
| end  | String|Oui|Non|

### Objet ConstraintPriority de type Boolean
| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
| priority |Integer|Oui|Oui (-1)|
|Value |Boolean |Oui|Non|

### Objet Module

| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
|idModule|Integer|Oui|Non|
|nbWeekOfModule|Integer|Oui|Non|
|nbHourOfModule|Integer|Oui|Non|
|listIdModulePrerequisite|Liste de Integer|Non|Non|
|listIdModuleOptional|Liste de Integer|Non|Non|
|listClasses|Liste de Classes|Oui|Non|

### Objet Classes
| Atribut | Type | Obligatoire | Valeur par défaut |
|---------|------|-------------|-------------------|
|idClasses|String|Oui|Non|
|IdPlace|Integer|Oui|Non|
|period|Period|Oui|Non|
|realDuration|Integer|Oui|Non|



### Objet Calendar (Objet de sortie)

| Atribut | Type | 
|---------|------|
|listClasses|Liste de ClassesCalendar|
|constraint|Liste de ConstraintRespected|

### Objet ClassesCalendar
   
| Atribut | Type | 
|---------|------|
|start|String|
|end|String|
|idModule|int|
|idClasses|String|
|constraints|Liste de ConstraintRespected par ordre de priorité décroissante|

### Objet ConstraintRespected
   
| Atribut | Type | Valeur possible |
|---------|------|-------------|
|isRespected|Boolean| |
|name|String|Fréquence de formation de %d semaines et de %d semaines en entreprise|
|||Lieu|
|||Modules prérequis|
|||Périodes exclues du "date de début" au "date de fin"|
|||Périodes inclues du "date de début" au "date de fin"|
|||"Nombre de stagiaire" stagiaires de la même entreprise|
|||Stagiaires recquis|

Exemple des paramètres en entrée :

```json
[
 {
   "periodOfTraining": {
     "start": "2017-01-02",
     "end": "2019-11-24"
   },
   "numberOfCalendarToFound": 5,
   "constraints": {
     "place": {
       "priority": 7,
       "value": 10
     },
     "annualNumberOfHour": {
       "priority": -1,
       "value": -1
     },
     "maxDurationOfTraining": {
       "priority": -1,
       "value": -1
     },
     "trainingFrequency": {
       "priority": 8,
       "value": {
         "maxWeekInTraining": 3,
         "minWeekInCompany": 3
       }
     },
     "maxStudentInTraining": {
       "priority": -1,
       "value": {
         "maxStudentInTraining": -1,
         "listStudentCompany": []
       }
     },
     "listStudentRequired": [],
     "listPeriodeOfTrainingExclusion": [
       {
         "priority": 5,
         "value": {
           "start": "2018-05-21",
           "end": "2018-05-27"
         }
       }
     ],
     "listPeriodeOfTrainingInclusion": [
       {
         "priority": 4,
         "value": {
           "start": "2018-04-02",
           "end": "2018-04-06"
         }
       }
     ],
     "prerequisModule": {
       "priority": -1,
       "value": true
     }
   },
   "moduleOfTraining": [
     {
       "idModule": 708,
       "nbWeekOfModule": 3,
       "nbHourOfModule": 105,
       "listIdModulePrerequisite": [],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "D090B325-1AB8-48D6-99C5-E0585D14D05A",
           "period": {
             "start": "2018-11-26",
             "end": "2018-11-30"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "8DCF25E3-1B22-47AF-9400-C891A50240A3",
           "period": {
             "start": "2018-03-19",
             "end": "2018-03-23"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "8FF17DC2-3AE1-4E39-BCDE-B115D6536F61",
           "period": {
             "start": "2018-06-11",
             "end": "2018-06-15"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "91163186-74AE-4458-8B12-E5106F8DC105",
           "period": {
             "start": "2018-03-19",
             "end": "2018-03-23"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "23C7F6A7-D917-4C3A-84A7-1FFEA8F83016",
           "period": {
             "start": "2018-12-03",
             "end": "2018-12-07"
           },
           "realDuration": 35,
           "idPlace": 10
         },
         {
           "idClasses": "9D39D0F3-395F-4C02-8DCF-222934035F4A",
           "period": {
             "start": "2018-04-09",
             "end": "2018-04-13"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "2BCA7AFD-9A08-479C-B1BA-0B09CD4DF4A6",
           "period": {
             "start": "2018-01-29",
             "end": "2018-02-02"
           },
           "realDuration": 35,
           "idPlace": 12
         },
         {
           "idClasses": "9004BC97-0BBA-4CDA-BB17-439B7C6B3025",
           "period": {
             "start": "2018-10-01",
             "end": "2018-10-05"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "88EED523-8B7C-4F5C-B71A-3D29C589FDB8",
           "period": {
             "start": "2018-04-09",
             "end": "2018-04-13"
           },
           "realDuration": 35,
           "idPlace": 10
         },
         {
           "idClasses": "77D66D8A-DEFD-44F6-A158-65DD5A34400E",
           "period": {
             "start": "2017-12-11",
             "end": "2017-12-15"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "1EB21C6B-7E5F-4921-A3AB-6D64053DA378",
           "period": {
             "start": "2018-04-09",
             "end": "2018-04-13"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "C24C868C-1BD2-4FC8-89D4-6E18F47F23C0",
           "period": {
             "start": "2018-06-11",
             "end": "2018-06-15"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "8197E1DD-1169-4E74-82CF-A0576081D58D",
           "period": {
             "start": "2017-12-18",
             "end": "2017-12-22"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "27C4662D-83FD-4A38-B3B3-AA8CB7F292ED",
           "period": {
             "start": "2018-04-09",
             "end": "2018-04-13"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "3AA758F1-2C0A-4570-AA3F-85F84117E429",
           "period": {
             "start": "2018-10-01",
             "end": "2018-10-05"
           },
           "realDuration": 35,
           "idPlace": 1
         }
       ]
     },
     {
       "idModule": 20,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "9AC9F5B9-BE0F-418D-AC3C-00EBB8582246",
           "period": {
             "start": "2018-03-26",
             "end": "2018-03-30"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "EEF20121-7745-4C55-8259-06ED9888E0A4",
           "period": {
             "start": "2018-03-26",
             "end": "2018-03-30"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "20D8C8D2-2FC7-4922-A4C6-1CAFF10C15D4",
           "period": {
             "start": "2018-04-16",
             "end": "2018-04-20"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "7CCFF5C3-EEC0-486B-ADF9-1FAD5977D3B9",
           "period": {
             "start": "2018-04-16",
             "end": "2018-04-20"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "49384010-C777-47F7-8BAF-25E8271AC403",
           "period": {
             "start": "2018-04-16",
             "end": "2018-04-20"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "13FDBD8F-8F18-4A41-8B76-2F63CDF62FC5",
           "period": {
             "start": "2018-06-18",
             "end": "2018-06-22"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "F02282BF-C22E-440B-BA13-31B6DABF19F5",
           "period": {
             "start": "2018-04-16",
             "end": "2018-04-20"
           },
           "realDuration": 35,
           "idPlace": 10
         },
         {
           "idClasses": "A7F50F72-8930-4C01-93F7-32388A4B91E2",
           "period": {
             "start": "2018-12-10",
             "end": "2018-12-14"
           },
           "realDuration": 35,
           "idPlace": 10
         },
         {
           "idClasses": "3F2BA010-10EA-412E-BF94-33E25B078A46",
           "period": {
             "start": "2018-10-08",
             "end": "2018-10-12"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "71E6F84D-928F-41AE-B456-4321A8848462",
           "period": {
             "start": "2018-01-22",
             "end": "2018-01-26"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "5D74CB18-FB70-4412-823C-56B26A3B123F",
           "period": {
             "start": "2018-06-18",
             "end": "2018-06-22"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "B2165BF1-133F-4502-A621-6683E9929885",
           "period": {
             "start": "2018-12-03",
             "end": "2018-12-07"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "A38899A4-DC5A-4B86-8FC6-8F7285513726",
           "period": {
             "start": "2018-02-05",
             "end": "2018-02-09"
           },
           "realDuration": 35,
           "idPlace": 12
         },
         {
           "idClasses": "BC2762CC-2544-4D01-8B47-B25DC6C07B12",
           "period": {
             "start": "2018-10-08",
             "end": "2018-10-12"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "263AAA5E-994E-4D2B-AEFA-EED5E6B2639C",
           "period": {
             "start": "2018-01-02",
             "end": "2018-01-05"
           },
           "realDuration": 35,
           "idPlace": 1
         }
       ]
     },
     {
       "idModule": 21,
       "nbWeekOfModule": 2,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         20
       ],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "46DB575D-76BB-40EC-BEBA-E408F4EE158B",
           "period": {
             "start": "2018-04-03",
             "end": "2018-04-06"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "69D4F236-9A75-41C5-B103-ED63FABDDFE2",
           "period": {
             "start": "2018-10-15",
             "end": "2018-10-19"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "B0BEE6FA-0DB7-4C3B-BAC1-B989FB149FD7",
           "period": {
             "start": "2018-04-23",
             "end": "2018-04-27"
           },
           "realDuration": 35,
           "idPlace": 10
         },
         {
           "idClasses": "188C569B-8C57-4EA2-9A1C-BD1A422C675E",
           "period": {
             "start": "2018-04-23",
             "end": "2018-04-27"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "427EA863-2410-47C1-9825-B15DEB1BC565",
           "period": {
             "start": "2018-10-15",
             "end": "2018-10-19"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "E286D7DC-EA10-4FBD-8A0A-C9C25919ADE0",
           "period": {
             "start": "2018-02-12",
             "end": "2018-02-16"
           },
           "realDuration": 35,
           "idPlace": 12
         },
         {
           "idClasses": "7A164585-9AC9-4B8F-9177-D19F14D8FECE",
           "period": {
             "start": "2018-01-29",
             "end": "2018-02-02"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "C3AFAF49-73E8-4737-AA7A-8EEC81927D16",
           "period": {
             "start": "2018-12-17",
             "end": "2018-12-21"
           },
           "realDuration": 35,
           "idPlace": 10
         },
         {
           "idClasses": "F4F54845-1C84-4EB7-8F6A-5FD020B54B01",
           "period": {
             "start": "2018-12-10",
             "end": "2018-12-14"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "C73EEC8A-481A-487A-A896-7E77C8A78F5D",
           "period": {
             "start": "2018-04-03",
             "end": "2018-04-06"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "C5E55386-6F6F-4D49-BDB6-4661BCFD745F",
           "period": {
             "start": "2018-04-23",
             "end": "2018-04-27"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "F15DB3EA-1AA8-4D13-8B96-48C3E58CE504",
           "period": {
             "start": "2018-04-23",
             "end": "2018-04-27"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "602DC652-F338-4AB7-83C3-249FDFD49AD2",
           "period": {
             "start": "2018-06-25",
             "end": "2018-06-29"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "7F4050E5-2B0A-4EAD-9847-080E8207296A",
           "period": {
             "start": "2018-06-25",
             "end": "2018-06-29"
           },
           "realDuration": 35,
           "idPlace": 11
         }
       ]
     },
     {
       "idModule": 725,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         708
       ],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "79125B83-EF81-4387-AF74-7FDFDF86E8A8",
           "period": {
             "start": "2018-05-14",
             "end": "2018-05-25"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "85239D35-A51E-4430-8B4F-8B05E5159614",
           "period": {
             "start": "2018-04-09",
             "end": "2018-04-20"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "A213824B-6763-4780-AC44-72A3B327D137",
           "period": {
             "start": "2018-12-17",
             "end": "2018-12-21"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "7C6D3F41-9EA3-41B9-98F5-5A2D5955E2CE",
           "period": {
             "start": "2018-05-14",
             "end": "2018-05-18"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "82B76FAB-C89C-41A7-AC7F-3ECFD6E71643",
           "period": {
             "start": "2018-05-14",
             "end": "2018-05-18"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "F818424D-09CA-4E5A-A405-42DDEEBF1B64",
           "period": {
             "start": "2018-07-02",
             "end": "2018-07-13"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "BD621130-84B8-4BA6-9EBA-4D75AE05684B",
           "period": {
             "start": "2018-10-22",
             "end": "2018-11-02"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "77E51E57-4DC7-4C03-95F2-52D8FFDC37B2",
           "period": {
             "start": "2018-04-30",
             "end": "2018-05-04"
           },
           "realDuration": 70,
           "idPlace": 10
         },
         {
           "idClasses": "7F8F6DB0-54A7-4E74-A2FC-58A96E11F60E",
           "period": {
             "start": "2019-01-07",
             "end": "2019-01-18"
           },
           "realDuration": 70,
           "idPlace": 10
         },
         {
           "idClasses": "1D13DBC1-8AF0-4293-9BE0-19F53994F350",
           "period": {
             "start": "2018-02-19",
             "end": "2018-03-02"
           },
           "realDuration": 70,
           "idPlace": 12
         },
         {
           "idClasses": "C2D24013-85C0-4661-A498-23D2F8CAA962",
           "period": {
             "start": "2018-07-02",
             "end": "2018-07-13"
           },
           "realDuration": 70,
           "idPlace": 11
         },
         {
           "idClasses": "B887EFE6-DCD8-4473-8615-E660E5A64FC0",
           "period": {
             "start": "2018-04-09",
             "end": "2018-04-20"
           },
           "realDuration": 70,
           "idPlace": 11
         },
         {
           "idClasses": "9AF796A9-ABF2-45EC-8068-F616B71A01D6",
           "period": {
             "start": "2018-04-30",
             "end": "2018-05-04"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "87C761B2-D842-4182-B044-FBD5E7AB0647",
           "period": {
             "start": "2018-04-30",
             "end": "2018-05-04"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "FA99B569-3A8E-48FC-84E5-AE08661E90A4",
           "period": {
             "start": "2019-01-07",
             "end": "2019-01-11"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "FF2F4269-F3FF-40B4-928F-B266146EF73F",
           "period": {
             "start": "2018-10-22",
             "end": "2018-11-02"
           },
           "realDuration": 70,
           "idPlace": 1
         }
       ]
     },
     {
       "idModule": 730,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         725
       ],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "3025FE4D-6C48-429F-B140-9CF881B8DF11",
           "period": {
             "start": "2018-04-23",
             "end": "2018-05-04"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "058B4374-63DC-4416-A2E9-948160022AC8",
           "period": {
             "start": "2019-01-21",
             "end": "2019-02-01"
           },
           "realDuration": 70,
           "idPlace": 10
         },
         {
           "idClasses": "460B6F26-60E3-46B1-B5DA-8222CA7E7508",
           "period": {
             "start": "2018-03-05",
             "end": "2018-03-16"
           },
           "realDuration": 70,
           "idPlace": 12
         },
         {
           "idClasses": "F679E790-176B-4E64-9EE4-7DDA8F7CDE57",
           "period": {
             "start": "2018-06-25",
             "end": "2018-07-06"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "7D4FA370-30F2-4F59-9E3C-6D53C01DAA7C",
           "period": {
             "start": "2019-01-14",
             "end": "2019-01-25"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "9D2AF640-BC7A-48D8-BB73-3890A44972E5",
           "period": {
             "start": "2018-07-16",
             "end": "2018-07-27"
           },
           "realDuration": 70,
           "idPlace": 11
         },
         {
           "idClasses": "AB448A09-D837-481D-A51E-FDCC1ADEEEDE",
           "period": {
             "start": "2018-05-22",
             "end": "2018-06-01"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "8B4BF1A0-9E34-4ACC-AB03-FFEC69085EDA",
           "period": {
             "start": "2018-05-22",
             "end": "2018-06-01"
           },
           "realDuration": 70,
           "idPlace": 10
         },
         {
           "idClasses": "7D237DD2-4F1C-41DB-9B09-D233A5191A18",
           "period": {
             "start": "2018-11-05",
             "end": "2018-11-16"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "5321A049-620F-41C5-B0B8-D85F07716661",
           "period": {
             "start": "2018-11-05",
             "end": "2018-11-16"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "F67D20B5-7972-433B-AE34-CB9CB30085CF",
           "period": {
             "start": "2018-05-22",
             "end": "2018-06-01"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "E2528FDB-2DCB-49F5-BF42-CE4C3BD0E6DB",
           "period": {
             "start": "2018-07-16",
             "end": "2018-07-27"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "FAFE4C71-4220-48EB-BEF9-C836483B77B6",
           "period": {
             "start": "2018-04-23",
             "end": "2018-05-04"
           },
           "realDuration": 70,
           "idPlace": 11
         }
       ]
     },
     {
       "idModule": 541,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "7F6F1BEA-4AD3-409F-B7D4-1D8233BB72F9",
           "period": {
             "start": "2018-12-03",
             "end": "2018-12-07"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "1EFC4886-2EE8-4526-84CF-1DC2FCD3246F",
           "period": {
             "start": "2018-06-18",
             "end": "2018-06-22"
           },
           "realDuration": 35,
           "idPlace": 10
         },
         {
           "idClasses": "B050CD0E-2390-45C0-918B-1EACB3EFF211",
           "period": {
             "start": "2018-08-20",
             "end": "2018-08-24"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "CDF20F3C-94D4-4069-A640-00E3100FDAE1",
           "period": {
             "start": "2018-08-20",
             "end": "2018-08-24"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "DF59BE30-CBE4-4694-8470-5715606FE9C9",
           "period": {
             "start": "2018-05-28",
             "end": "2018-06-01"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "219CC390-7A58-477F-83D8-75278977BC67",
           "period": {
             "start": "2018-05-28",
             "end": "2018-06-01"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "3C6B1394-78A3-4EE8-A23D-85BABF0D24E2",
           "period": {
             "start": "2018-04-03",
             "end": "2018-04-06"
           },
           "realDuration": 35,
           "idPlace": 12
         },
         {
           "idClasses": "B41E4437-5145-498A-A1C4-8DF1BE253365",
           "period": {
             "start": "2018-06-18",
             "end": "2018-06-22"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "9CD9F784-76BC-4BA3-AA82-CABB25ACA379",
           "period": {
             "start": "2019-02-18",
             "end": "2019-02-22"
           },
           "realDuration": 35,
           "idPlace": 10
         },
         {
           "idClasses": "3616294E-D6BA-484F-AE40-B8DEF50FFA77",
           "period": {
             "start": "2018-06-18",
             "end": "2018-06-22"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "EC7AECCD-A8EE-436C-B9B5-D20A816744B7",
           "period": {
             "start": "2019-02-11",
             "end": "2019-02-15"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "B061528A-4125-400C-A348-BDDA7469D42F",
           "period": {
             "start": "2018-10-08",
             "end": "2018-10-12"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "D7D0B8AD-841E-4E77-9951-EFBD2EE80481",
           "period": {
             "start": "2018-12-03",
             "end": "2018-12-07"
           },
           "realDuration": 35,
           "idPlace": 1
         }
       ]
     },
     {
       "idModule": 544,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [],
       "listIdModuleOptional": [
         541
       ],
       "listClasses": [
         {
           "idClasses": "EDBF0441-50CC-4B38-A0C0-E0A79C95E2E9",
           "period": {
             "start": "2018-06-25",
             "end": "2018-07-13"
           },
           "realDuration": 105,
           "idPlace": 10
         },
         {
           "idClasses": "2C2B0501-487C-46F7-A98C-E1549BD944F5",
           "period": {
             "start": "2018-08-27",
             "end": "2018-09-14"
           },
           "realDuration": 105,
           "idPlace": 11
         },
         {
           "idClasses": "C1F8614E-DA58-4620-A20E-E6B6461D746B",
           "period": {
             "start": "2019-02-25",
             "end": "2019-03-15"
           },
           "realDuration": 105,
           "idPlace": 10
         },
         {
           "idClasses": "3145CAFA-0E4B-420A-8265-B8143624B40B",
           "period": {
             "start": "2019-01-07",
             "end": "2019-01-11"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "966E9CF6-1126-474A-A8CC-D01EA99E3735",
           "period": {
             "start": "2018-12-10",
             "end": "2018-12-21"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "E3BE7F4B-A4B3-4675-80BA-87381D26EBC1",
           "period": {
             "start": "2019-02-18",
             "end": "2019-03-08"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "E9AA5E81-77B0-43DB-B2F2-A77F62D16933",
           "period": {
             "start": "2018-06-04",
             "end": "2018-06-22"
           },
           "realDuration": 105,
           "idPlace": 11
         },
         {
           "idClasses": "AD33FBE0-C07F-4737-9C2B-7ED85F26805C",
           "period": {
             "start": "2018-06-25",
             "end": "2018-07-13"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "1B147CB2-F7DC-45E4-BDD6-6BDF029A362D",
           "period": {
             "start": "2019-01-07",
             "end": "2019-01-11"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "A8FD9341-5439-4C7E-AFF4-6966F14F5BC0",
           "period": {
             "start": "2018-12-10",
             "end": "2018-12-21"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "EB2ACA76-6421-4514-A1B8-5C1781B98609",
           "period": {
             "start": "2018-06-04",
             "end": "2018-06-22"
           },
           "realDuration": 105,
           "idPlace": 2
         },
         {
           "idClasses": "0B839480-E37D-48D8-BABB-55B61ED78431",
           "period": {
             "start": "2018-11-19",
             "end": "2018-11-30"
           },
           "realDuration": 105,
           "idPlace": 2
         },
         {
           "idClasses": "934042C9-1EF3-4B64-9F25-4D153C54042F",
           "period": {
             "start": "2018-08-27",
             "end": "2018-09-14"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "E9093CE0-CFFA-4962-8276-442B4F3FA86C",
           "period": {
             "start": "2018-06-25",
             "end": "2018-07-13"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "AB2AE0F6-793C-4DB9-9701-297506E39300",
           "period": {
             "start": "2018-10-15",
             "end": "2018-10-19"
           },
           "realDuration": 105,
           "idPlace": 2
         },
         {
           "idClasses": "85479554-E63F-44D0-81A9-0BD66A5F262D",
           "period": {
             "start": "2018-04-09",
             "end": "2018-04-27"
           },
           "realDuration": 105,
           "idPlace": 12
         }
       ]
     },
     {
       "idModule": 302,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         725
       ],
       "listIdModuleOptional": [
         541
       ],
       "listClasses": [
         {
           "idClasses": "1F2053DA-D71F-4D3A-8183-466B19E34B39",
           "period": {
             "start": "2019-03-11",
             "end": "2019-03-22"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "A6F82AF0-CAB6-42FB-A1B2-56B7A7ED1CEF",
           "period": {
             "start": "2018-07-16",
             "end": "2018-07-27"
           },
           "realDuration": 70,
           "idPlace": 10
         },
         {
           "idClasses": "601CC889-8343-4E62-89AE-540022CDF3F4",
           "period": {
             "start": "2019-01-14",
             "end": "2019-01-25"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "CB4DD72B-0C0E-45E7-932E-3A1D75F9841C",
           "period": {
             "start": "2018-09-17",
             "end": "2018-09-28"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "86BD5A87-8796-4841-96C2-30999B3ABE7A",
           "period": {
             "start": "2018-01-29",
             "end": "2018-02-09"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "C90FF4CC-8847-4895-A0F4-074717D248D1",
           "period": {
             "start": "2018-06-25",
             "end": "2018-07-06"
           },
           "realDuration": 70,
           "idPlace": 11
         },
         {
           "idClasses": "00CB9CE4-0DEA-47A3-95B4-717EB14BA7EB",
           "period": {
             "start": "2019-03-18",
             "end": "2019-03-29"
           },
           "realDuration": 70,
           "idPlace": 10
         },
         {
           "idClasses": "854AB776-5894-4CEB-BE4B-A6702A2F1398",
           "period": {
             "start": "2018-07-16",
             "end": "2018-07-27"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "C2B13BEE-AF8C-4B01-9138-96472CED391C",
           "period": {
             "start": "2019-01-07",
             "end": "2019-01-18"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "22D0DC71-08F4-4FFD-BC5A-8E5F9DD1F65C",
           "period": {
             "start": "2018-04-30",
             "end": "2018-05-04"
           },
           "realDuration": 70,
           "idPlace": 12
         },
         {
           "idClasses": "9C159163-395C-4C42-8A55-8205C8A0AADD",
           "period": {
             "start": "2018-07-16",
             "end": "2018-07-27"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "AE328890-2F0D-4159-9E8F-83614E398E29",
           "period": {
             "start": "2018-05-14",
             "end": "2018-05-18"
           },
           "realDuration": 70,
           "idPlace": 12
         },
         {
           "idClasses": "91A46A5E-F1B3-41BF-8B89-E56D82E1952D",
           "period": {
             "start": "2019-01-14",
             "end": "2019-01-25"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "2719B3A2-AB19-49C5-8BF2-FDB79C75A0E6",
           "period": {
             "start": "2018-06-25",
             "end": "2018-07-06"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "B9ACABF3-51FA-460B-9016-FE647A831F2D",
           "period": {
             "start": "2018-09-17",
             "end": "2018-09-28"
           },
           "realDuration": 70,
           "idPlace": 11
         }
       ]
     },
     {
       "idModule": 727,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         302
       ],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "03DFABF1-9405-499B-8F73-AC917ED5532D",
           "period": {
             "start": "2019-03-25",
             "end": "2019-04-12"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "33A95D4A-3C4F-46AC-B107-ADF5BABD63DA",
           "period": {
             "start": "2019-01-28",
             "end": "2019-02-15"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "16E23474-A904-4CEB-B0B4-C5EB9963698F",
           "period": {
             "start": "2019-02-18",
             "end": "2019-03-08"
           },
           "realDuration": 105,
           "idPlace": 2
         },
         {
           "idClasses": "F10880DF-9F18-41B3-85E1-C77C5031AD4F",
           "period": {
             "start": "2018-10-01",
             "end": "2018-10-19"
           },
           "realDuration": 105,
           "idPlace": 11
         },
         {
           "idClasses": "E24C6E81-0971-4C93-A749-EB516155B848",
           "period": {
             "start": "2018-05-22",
             "end": "2018-06-08"
           },
           "realDuration": 105,
           "idPlace": 12
         },
         {
           "idClasses": "BEC4E65E-3A92-431A-AA83-27C3352B53F3",
           "period": {
             "start": "2018-08-20",
             "end": "2018-08-24"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "6A4C51CD-529F-4B6A-B8C2-0E1A7A7FC51C",
           "period": {
             "start": "2018-08-20",
             "end": "2018-08-24"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "FC681319-FD7B-4250-AD48-09075D0CC074",
           "period": {
             "start": "2018-07-30",
             "end": "2018-08-10"
           },
           "realDuration": 105,
           "idPlace": 10
         },
         {
           "idClasses": "3E17AF8A-FC24-4BDA-8010-0203826B9899",
           "period": {
             "start": "2019-04-01",
             "end": "2019-04-19"
           },
           "realDuration": 105,
           "idPlace": 10
         },
         {
           "idClasses": "6A739FAD-0733-45AE-AB87-5623600452B0",
           "period": {
             "start": "2018-10-01",
             "end": "2018-10-19"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "C649E589-F653-4704-BC22-446E44625734",
           "period": {
             "start": "2018-07-09",
             "end": "2018-07-27"
           },
           "realDuration": 105,
           "idPlace": 11
         },
         {
           "idClasses": "6075D1E3-FD17-453D-8B86-5B9B4CBFA5AC",
           "period": {
             "start": "2018-07-30",
             "end": "2018-08-10"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "40C0A526-F3AB-4FEC-B730-8C3AF52CED7E",
           "period": {
             "start": "2018-07-09",
             "end": "2018-07-27"
           },
           "realDuration": 105,
           "idPlace": 2
         },
         {
           "idClasses": "4ECFA3E8-2D13-4BF2-A49A-8167936CF76E",
           "period": {
             "start": "2018-07-30",
             "end": "2018-08-10"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "6C77C766-656F-4DAE-93E8-84F3E7E45BDB",
           "period": {
             "start": "2019-01-28",
             "end": "2019-02-15"
           },
           "realDuration": 105,
           "idPlace": 1
         }
       ]
     },
     {
       "idModule": 728,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         725
       ],
       "listIdModuleOptional": [
         302
       ],
       "listClasses": [
         {
           "idClasses": "115E3C13-AA90-4D23-A8C9-9A8FCC942F4D",
           "period": {
             "start": "2019-06-03",
             "end": "2019-06-14"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "D45D5334-127F-450D-A4D5-9E35D80FEC6F",
           "period": {
             "start": "2018-07-30",
             "end": "2018-08-10"
           },
           "realDuration": 70,
           "idPlace": 11
         },
         {
           "idClasses": "20E21241-4344-483B-BBA2-6B776FF9A5C7",
           "period": {
             "start": "2019-02-18",
             "end": "2019-03-01"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "2F8FC23C-7DF0-48ED-B0B9-3C542E439217",
           "period": {
             "start": "2018-08-27",
             "end": "2018-09-07"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "445A8790-B375-403F-8134-5646089D5A19",
           "period": {
             "start": "2019-04-23",
             "end": "2019-05-03"
           },
           "realDuration": 70,
           "idPlace": 10
         },
         {
           "idClasses": "4F947DFC-520B-421C-98E8-064181CE9270",
           "period": {
             "start": "2019-04-08",
             "end": "2019-04-19"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "CDAFADCB-7FF8-4B8C-8DBE-1C3A57069C63",
           "period": {
             "start": "2018-10-15",
             "end": "2018-10-26"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "98B4405A-1760-4C4D-A131-1F23D754E51F",
           "period": {
             "start": "2018-08-27",
             "end": "2018-09-07"
           },
           "realDuration": 70,
           "idPlace": 10
         },
         {
           "idClasses": "D8DC6F44-3BA8-4722-81F9-2F2B89BBD8FC",
           "period": {
             "start": "2018-12-10",
             "end": "2018-12-21"
           },
           "realDuration": 70,
           "idPlace": 11
         },
         {
           "idClasses": "446C385E-707F-4113-996D-DCF7DC16D216",
           "period": {
             "start": "2018-06-11",
             "end": "2018-06-22"
           },
           "realDuration": 70,
           "idPlace": 12
         },
         {
           "idClasses": "9E10FA5B-8D32-4844-A86F-E4BD5133D1BC",
           "period": {
             "start": "2019-08-26",
             "end": "2019-09-06"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "0950D373-0902-4784-94BC-BEC90A8BD01A",
           "period": {
             "start": "2018-09-17",
             "end": "2018-09-28"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "F522EAF4-5A9B-4927-9919-B383A3116AB0",
           "period": {
             "start": "2018-10-22",
             "end": "2018-11-02"
           },
           "realDuration": 70,
           "idPlace": 1
         }
       ]
     },
     {
       "idModule": 729,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         728
       ],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "5DE27295-322F-4A96-8DAC-C3DC0BAA19F9",
           "period": {
             "start": "2019-03-04",
             "end": "2019-03-08"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "C357AB39-F257-4720-A965-DB9FB923AEA8",
           "period": {
             "start": "2018-10-29",
             "end": "2018-11-02"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "58E280BA-5356-4816-874F-E4FF4F5C8C22",
           "period": {
             "start": "2019-05-06",
             "end": "2019-05-10"
           },
           "realDuration": 35,
           "idPlace": 10
         },
         {
           "idClasses": "F53B456F-CBE6-424C-9BAF-594CAB2190AE",
           "period": {
             "start": "2019-06-17",
             "end": "2019-06-21"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "DEB4033D-5E7A-4785-9C74-4BB837C555A0",
           "period": {
             "start": "2018-10-01",
             "end": "2018-10-05"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "9A0EC673-1147-4DC7-BE4C-38D70CBC4D26",
           "period": {
             "start": "2018-06-25",
             "end": "2018-06-29"
           },
           "realDuration": 35,
           "idPlace": 12
         },
         {
           "idClasses": "A7EEED71-3F90-47B5-AB71-38F1442E834D",
           "period": {
             "start": "2018-11-05",
             "end": "2018-11-09"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "F20D680F-2940-48D8-92AA-695CD09E40B1",
           "period": {
             "start": "2019-04-23",
             "end": "2019-04-26"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "CAB51343-77CE-4A05-9A27-75BDA17AFAAD",
           "period": {
             "start": "2018-09-10",
             "end": "2018-09-14"
           },
           "realDuration": 35,
           "idPlace": 10
         },
         {
           "idClasses": "1A53B31D-5197-4855-99BE-744677A5D04B",
           "period": {
             "start": "2019-09-09",
             "end": "2019-09-13"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "1931AEFE-92B7-4E2E-AFD2-AAA03DC369A7",
           "period": {
             "start": "2019-01-07",
             "end": "2019-01-11"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "A6ECA816-248F-4361-BEC0-9D9622BE6C1D",
           "period": {
             "start": "2018-09-10",
             "end": "2018-09-14"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "1DB7E74B-FEB9-43B7-88F1-A1D7C149E512",
           "period": {
             "start": "2018-08-13",
             "end": "2018-08-17"
           },
           "realDuration": 35,
           "idPlace": 11
         }
       ]
     },
     {
       "idModule": 34,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         302
       ],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "29C7B5D7-494E-45F2-A4B5-0BF69055A08E",
           "period": {
             "start": "2018-07-30",
             "end": "2018-08-10"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "079857A4-C961-4CA5-A3F9-0F7697F379D1",
           "period": {
             "start": "2019-04-08",
             "end": "2019-04-19"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "F1D989DA-24A7-4D62-A248-2A6615F93994",
           "period": {
             "start": "2018-10-22",
             "end": "2018-11-02"
           },
           "realDuration": 70,
           "idPlace": 11
         },
         {
           "idClasses": "A140AE65-7FC8-4F93-AEE8-38A9D349EC19",
           "period": {
             "start": "2018-08-27",
             "end": "2018-09-07"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "3AB4CDEA-A36F-426F-87D1-AA7F45BAF6B2",
           "period": {
             "start": "2019-02-18",
             "end": "2019-03-01"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "217BF397-CB75-430C-AE2A-FF76AEF2196F",
           "period": {
             "start": "2019-04-15",
             "end": "2019-04-26"
           },
           "realDuration": 70,
           "idPlace": 1
         }
       ]
     },
     {
       "idModule": 731,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [],
       "listIdModuleOptional": [
         544
       ],
       "listClasses": [
         {
           "idClasses": "C9EAD594-44CE-4F79-88E2-D1B75D274942",
           "period": {
             "start": "2018-09-10",
             "end": "2018-09-14"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "FABB62A4-66D5-458C-846C-E6C8927249DC",
           "period": {
             "start": "2019-03-04",
             "end": "2019-03-08"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "B68EA7BA-5871-4093-95C9-79B5CE0C6C71",
           "period": {
             "start": "2019-05-20",
             "end": "2019-05-24"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "D7D4E588-3FC1-4F43-918E-823773D7BF57",
           "period": {
             "start": "2018-11-05",
             "end": "2018-11-09"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "1C46C421-3EF5-4938-8C81-9E4F556A00D5",
           "period": {
             "start": "2019-04-29",
             "end": "2019-05-03"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "F6F85C5D-AD6F-47C1-BB5F-9FDC333DD6C4",
           "period": {
             "start": "2018-08-13",
             "end": "2018-08-17"
           },
           "realDuration": 35,
           "idPlace": 2
         }
       ]
     },
     {
       "idModule": 36,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "7487CDF9-CD97-44A6-8ECF-F9DF66748166",
           "period": {
             "start": "2018-11-12",
             "end": "2018-11-16"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "748346CF-194A-4F26-9C97-ED3790BF85C8",
           "period": {
             "start": "2019-05-06",
             "end": "2019-05-10"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "9D8B2B45-4750-4272-AF0F-718E62F037D4",
           "period": {
             "start": "2019-03-11",
             "end": "2019-03-15"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "993F8A4D-CBA5-412C-9966-3F3630F630E9",
           "period": {
             "start": "2018-09-17",
             "end": "2018-09-21"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "3801F745-4FED-4E05-B9A6-4998DBC7C480",
           "period": {
             "start": "2018-08-20",
             "end": "2018-08-24"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "01CF703D-66AD-413B-8940-46BF4684F885",
           "period": {
             "start": "2019-05-27",
             "end": "2019-05-31"
           },
           "realDuration": 35,
           "idPlace": 2
         }
       ]
     },
     {
       "idModule": 560,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [],
       "listIdModuleOptional": [
         541
       ],
       "listClasses": [
         {
           "idClasses": "4EBD9C3F-373D-468D-BE7C-0C54BB88410C",
           "period": {
             "start": "2019-07-01",
             "end": "2019-07-19"
           },
           "realDuration": 105,
           "idPlace": 2
         },
         {
           "idClasses": "AD6EFD1D-80DA-4063-8D3B-411954AB3F32",
           "period": {
             "start": "2019-03-18",
             "end": "2019-04-05"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "59B0F1DC-DAEB-4BF4-BB81-484F3919F66B",
           "period": {
             "start": "2018-11-19",
             "end": "2018-12-07"
           },
           "realDuration": 105,
           "idPlace": 11
         },
         {
           "idClasses": "75EB593F-DF78-4A14-848B-5313A4BE520B",
           "period": {
             "start": "2018-09-24",
             "end": "2018-10-12"
           },
           "realDuration": 105,
           "idPlace": 1
         },
         {
           "idClasses": "CFC787BF-FE59-4FE7-8E4F-7C2AA5C47CAE",
           "period": {
             "start": "2018-08-27",
             "end": "2018-09-14"
           },
           "realDuration": 105,
           "idPlace": 2
         },
         {
           "idClasses": "B4B5E257-3F55-4555-87EE-8F32F5B5DC3A",
           "period": {
             "start": "2019-05-13",
             "end": "2019-05-31"
           },
           "realDuration": 105,
           "idPlace": 1
         }
       ]
     },
     {
       "idModule": 566,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         560
       ],
       "listIdModuleOptional": [
         728
       ],
       "listClasses": [
         {
           "idClasses": "D9E80C62-D60F-4D41-A938-80C44A7FFAE4",
           "period": {
             "start": "2018-11-05",
             "end": "2018-11-09"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "EA4755F1-3C0E-43D7-9326-A1C999F30E7D",
           "period": {
             "start": "2019-01-14",
             "end": "2019-01-18"
           },
           "realDuration": 35,
           "idPlace": 11
         },
         {
           "idClasses": "174DEC07-CCB1-482F-8CE3-910DDA1F7467",
           "period": {
             "start": "2019-06-24",
             "end": "2019-06-28"
           },
           "realDuration": 35,
           "idPlace": 1
         },
         {
           "idClasses": "EBD828B0-4CF0-464D-876C-929B8A5C8E80",
           "period": {
             "start": "2019-09-16",
             "end": "2019-09-20"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "2AD6370A-5C01-49E7-9342-5EC4DF37783D",
           "period": {
             "start": "2019-10-21",
             "end": "2019-10-25"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "CA3B48C2-2729-489D-9BC5-33225F26337B",
           "period": {
             "start": "2018-10-08",
             "end": "2018-10-12"
           },
           "realDuration": 35,
           "idPlace": 2
         },
         {
           "idClasses": "D3E9EE90-4BDB-432B-823E-DEA0FA2B1B9D",
           "period": {
             "start": "2019-04-29",
             "end": "2019-05-03"
           },
           "realDuration": 35,
           "idPlace": 1
         }
       ]
     },
     {
       "idModule": 734,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         708,
         725
       ],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "B15F237E-0FA4-43F0-A647-85DB803BF6C5",
           "period": {
             "start": "2018-03-05",
             "end": "2018-03-16"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "85A5E18B-58E8-4C25-B95D-7E832D88D557",
           "period": {
             "start": "2018-07-30",
             "end": "2018-08-10"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "45AA1DB4-9BE1-4584-9D43-7E8F522D13AC",
           "period": {
             "start": "2018-11-19",
             "end": "2018-11-30"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "BAFD1012-9E8C-4D8D-9775-74056AB7E3E9",
           "period": {
             "start": "2019-02-04",
             "end": "2019-02-15"
           },
           "realDuration": 70,
           "idPlace": 10
         },
         {
           "idClasses": "49C58519-D02B-4473-B0A5-3FA251FF6490",
           "period": {
             "start": "2018-05-14",
             "end": "2018-05-25"
           },
           "realDuration": 70,
           "idPlace": 11
         },
         {
           "idClasses": "F9509972-B829-47FC-80AD-1FDF4DB35936",
           "period": {
             "start": "2018-08-20",
             "end": "2018-08-31"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "646789C9-51F0-4641-B6A3-26527F863A38",
           "period": {
             "start": "2018-06-04",
             "end": "2018-06-15"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "54263645-E9D2-4825-879C-E81C1C3E4A81",
           "period": {
             "start": "2018-05-14",
             "end": "2018-05-25"
           },
           "realDuration": 70,
           "idPlace": 2
         },
         {
           "idClasses": "A6FE3680-0EDF-439B-B8F3-EABAE3F1F3B0",
           "period": {
             "start": "2018-11-19",
             "end": "2018-11-30"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "84531CF4-AA01-4C59-8D34-E53A3B39F8DF",
           "period": {
             "start": "2018-07-30",
             "end": "2018-08-10"
           },
           "realDuration": 70,
           "idPlace": 11
         },
         {
           "idClasses": "DE0F7590-2F6D-42FE-9ED1-FCB2C6DB2B65",
           "period": {
             "start": "2018-03-19",
             "end": "2018-03-30"
           },
           "realDuration": 70,
           "idPlace": 12
         },
         {
           "idClasses": "9A34D0AF-C62D-4BAA-BBD6-CEAF67FFC7AE",
           "period": {
             "start": "2018-06-04",
             "end": "2018-06-15"
           },
           "realDuration": 70,
           "idPlace": 1
         },
         {
           "idClasses": "D820AA47-D8B8-47AA-91DE-D864EB65C4EA",
           "period": {
             "start": "2018-06-04",
             "end": "2018-06-15"
           },
           "realDuration": 70,
           "idPlace": 10
         },
         {
           "idClasses": "B58AAB97-439E-4D58-9FE5-B392733F5E6B",
           "period": {
             "start": "2019-01-28",
             "end": "2019-02-08"
           },
           "realDuration": 70,
           "idPlace": 1
         }
       ]
     },
     {
       "idModule": 817,
       "nbWeekOfModule": 1,
       "nbHourOfModule": 35,
       "listIdModulePrerequisite": [
         20,
         566,
         728,
         34,
         729,
         544,
         232,
         731
       ],
       "listIdModuleOptional": [],
       "listClasses": [
         {
           "idClasses": "2CF00DDF-FB7B-4C16-8C36-6C23B776FD86",
           "period": {
             "start": "2018-11-19",
             "end": "2018-11-20"
           },
           "realDuration": 14,
           "idPlace": 2
         },
         {
           "idClasses": "2DDD0DDF-FB7B-4C16-8C36-6C23B776FD86",
           "period": {
             "start": "2019-11-19",
             "end": "2019-11-20"
           },
           "realDuration": 14,
           "idPlace": 2
         }
       ]
     }
   ]
 }
]
```

Exemple des paramètres en sortie :

```json
[
  {
    "constraint": [
      {
        "priority": 8,
        "value": {
          "maxWeekInTraining": 3,
          "minWeekInCompany": 3
        },
        "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
        "respected": false
      },
      {
        "priority": 7,
        "value": 10,
        "name": "Lieu",
        "respected": false
      },
      {
        "priority": 5,
        "value": {
          "start": "2018-05-21",
          "end": "2018-05-27"
        },
        "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
        "respected": true
      },
      {
        "priority": 4,
        "value": {
          "start": "2018-04-02",
          "end": "2018-04-06"
        },
        "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
        "respected": true
      },
      {
        "priority": -1,
        "value": true,
        "name": "Modules prérequis",
        "respected": true
      }
    ],
    "cours": [
      {
        "start": "2018-03-26",
        "end": "2018-03-30",
        "idModule": 20,
        "idClasses": "9AC9F5B9-BE0F-418D-AC3C-00EBB8582246",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-03",
        "end": "2018-04-06",
        "idModule": 21,
        "idClasses": "46DB575D-76BB-40EC-BEBA-E408F4EE158B",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-09",
        "end": "2018-04-13",
        "idModule": 708,
        "idClasses": "88EED523-8B7C-4F5C-B71A-3D29C589FDB8",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-30",
        "end": "2018-05-04",
        "idModule": 725,
        "idClasses": "9AF796A9-ABF2-45EC-8068-F616B71A01D6",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-05-28",
        "end": "2018-06-01",
        "idModule": 541,
        "idClasses": "219CC390-7A58-477F-83D8-75278977BC67",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-06-04",
        "end": "2018-06-15",
        "idModule": 734,
        "idClasses": "D820AA47-D8B8-47AA-91DE-D864EB65C4EA",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-07-16",
        "end": "2018-07-27",
        "idModule": 302,
        "idClasses": "A6F82AF0-CAB6-42FB-A1B2-56B7A7ED1CEF",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-08-27",
        "end": "2018-09-07",
        "idModule": 728,
        "idClasses": "98B4405A-1760-4C4D-A131-1F23D754E51F",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-09-10",
        "end": "2018-09-14",
        "idModule": 729,
        "idClasses": "CAB51343-77CE-4A05-9A27-75BDA17AFAAD",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-12-10",
        "end": "2018-12-21",
        "idModule": 544,
        "idClasses": "966E9CF6-1126-474A-A8CC-D01EA99E3735",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-01-14",
        "end": "2019-01-25",
        "idModule": 730,
        "idClasses": "7D4FA370-30F2-4F59-9E3C-6D53C01DAA7C",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-02-18",
        "end": "2019-03-01",
        "idModule": 34,
        "idClasses": "3AB4CDEA-A36F-426F-87D1-AA7F45BAF6B2",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-03-04",
        "end": "2019-03-08",
        "idModule": 731,
        "idClasses": "FABB62A4-66D5-458C-846C-E6C8927249DC",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-04-01",
        "end": "2019-04-19",
        "idModule": 727,
        "idClasses": "3E17AF8A-FC24-4BDA-8010-0203826B9899",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-05-27",
        "end": "2019-05-31",
        "idModule": 36,
        "idClasses": "01CF703D-66AD-413B-8940-46BF4684F885",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-07-01",
        "end": "2019-07-19",
        "idModule": 560,
        "idClasses": "4EBD9C3F-373D-468D-BE7C-0C54BB88410C",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-09-16",
        "end": "2019-09-20",
        "idModule": 566,
        "idClasses": "EBD828B0-4CF0-464D-876C-929B8A5C8E80",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-11-19",
        "end": "2019-11-20",
        "idModule": 817,
        "idClasses": "2DDD0DDF-FB7B-4C16-8C36-6C23B776FD86",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      }
    ]
  },
  {
    "constraint": [
      {
        "priority": 8,
        "value": {
          "maxWeekInTraining": 3,
          "minWeekInCompany": 3
        },
        "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
        "respected": false
      },
      {
        "priority": 7,
        "value": 10,
        "name": "Lieu",
        "respected": false
      },
      {
        "priority": 5,
        "value": {
          "start": "2018-05-21",
          "end": "2018-05-27"
        },
        "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
        "respected": true
      },
      {
        "priority": 4,
        "value": {
          "start": "2018-04-02",
          "end": "2018-04-06"
        },
        "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
        "respected": true
      },
      {
        "priority": -1,
        "value": true,
        "name": "Modules prérequis",
        "respected": true
      }
    ],
    "cours": [
      {
        "start": "2018-03-26",
        "end": "2018-03-30",
        "idModule": 20,
        "idClasses": "9AC9F5B9-BE0F-418D-AC3C-00EBB8582246",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-03",
        "end": "2018-04-06",
        "idModule": 21,
        "idClasses": "46DB575D-76BB-40EC-BEBA-E408F4EE158B",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-09",
        "end": "2018-04-13",
        "idModule": 708,
        "idClasses": "88EED523-8B7C-4F5C-B71A-3D29C589FDB8",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-30",
        "end": "2018-05-04",
        "idModule": 725,
        "idClasses": "9AF796A9-ABF2-45EC-8068-F616B71A01D6",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-05-28",
        "end": "2018-06-01",
        "idModule": 541,
        "idClasses": "DF59BE30-CBE4-4694-8470-5715606FE9C9",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-06-04",
        "end": "2018-06-15",
        "idModule": 734,
        "idClasses": "D820AA47-D8B8-47AA-91DE-D864EB65C4EA",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-07-16",
        "end": "2018-07-27",
        "idModule": 302,
        "idClasses": "A6F82AF0-CAB6-42FB-A1B2-56B7A7ED1CEF",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-08-27",
        "end": "2018-09-07",
        "idModule": 728,
        "idClasses": "98B4405A-1760-4C4D-A131-1F23D754E51F",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-09-10",
        "end": "2018-09-14",
        "idModule": 729,
        "idClasses": "CAB51343-77CE-4A05-9A27-75BDA17AFAAD",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-12-10",
        "end": "2018-12-21",
        "idModule": 544,
        "idClasses": "966E9CF6-1126-474A-A8CC-D01EA99E3735",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-01-14",
        "end": "2019-01-25",
        "idModule": 730,
        "idClasses": "7D4FA370-30F2-4F59-9E3C-6D53C01DAA7C",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-02-18",
        "end": "2019-03-01",
        "idModule": 34,
        "idClasses": "3AB4CDEA-A36F-426F-87D1-AA7F45BAF6B2",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-03-04",
        "end": "2019-03-08",
        "idModule": 731,
        "idClasses": "FABB62A4-66D5-458C-846C-E6C8927249DC",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-04-01",
        "end": "2019-04-19",
        "idModule": 727,
        "idClasses": "3E17AF8A-FC24-4BDA-8010-0203826B9899",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-05-27",
        "end": "2019-05-31",
        "idModule": 36,
        "idClasses": "01CF703D-66AD-413B-8940-46BF4684F885",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-07-01",
        "end": "2019-07-19",
        "idModule": 560,
        "idClasses": "4EBD9C3F-373D-468D-BE7C-0C54BB88410C",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-09-16",
        "end": "2019-09-20",
        "idModule": 566,
        "idClasses": "EBD828B0-4CF0-464D-876C-929B8A5C8E80",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-11-19",
        "end": "2019-11-20",
        "idModule": 817,
        "idClasses": "2DDD0DDF-FB7B-4C16-8C36-6C23B776FD86",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      }
    ]
  },
  {
    "constraint": [
      {
        "priority": 8,
        "value": {
          "maxWeekInTraining": 3,
          "minWeekInCompany": 3
        },
        "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
        "respected": false
      },
      {
        "priority": 7,
        "value": 10,
        "name": "Lieu",
        "respected": false
      },
      {
        "priority": 5,
        "value": {
          "start": "2018-05-21",
          "end": "2018-05-27"
        },
        "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
        "respected": true
      },
      {
        "priority": 4,
        "value": {
          "start": "2018-04-02",
          "end": "2018-04-06"
        },
        "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
        "respected": true
      },
      {
        "priority": -1,
        "value": true,
        "name": "Modules prérequis",
        "respected": true
      }
    ],
    "cours": [
      {
        "start": "2018-03-26",
        "end": "2018-03-30",
        "idModule": 20,
        "idClasses": "9AC9F5B9-BE0F-418D-AC3C-00EBB8582246",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-03",
        "end": "2018-04-06",
        "idModule": 21,
        "idClasses": "46DB575D-76BB-40EC-BEBA-E408F4EE158B",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-09",
        "end": "2018-04-13",
        "idModule": 708,
        "idClasses": "88EED523-8B7C-4F5C-B71A-3D29C589FDB8",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-30",
        "end": "2018-05-04",
        "idModule": 725,
        "idClasses": "9AF796A9-ABF2-45EC-8068-F616B71A01D6",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-06-04",
        "end": "2018-06-15",
        "idModule": 734,
        "idClasses": "D820AA47-D8B8-47AA-91DE-D864EB65C4EA",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-06-18",
        "end": "2018-06-22",
        "idModule": 541,
        "idClasses": "B41E4437-5145-498A-A1C4-8DF1BE253365",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-07-16",
        "end": "2018-07-27",
        "idModule": 302,
        "idClasses": "A6F82AF0-CAB6-42FB-A1B2-56B7A7ED1CEF",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-08-27",
        "end": "2018-09-07",
        "idModule": 728,
        "idClasses": "98B4405A-1760-4C4D-A131-1F23D754E51F",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-09-10",
        "end": "2018-09-14",
        "idModule": 729,
        "idClasses": "CAB51343-77CE-4A05-9A27-75BDA17AFAAD",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-12-10",
        "end": "2018-12-21",
        "idModule": 544,
        "idClasses": "966E9CF6-1126-474A-A8CC-D01EA99E3735",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-01-14",
        "end": "2019-01-25",
        "idModule": 730,
        "idClasses": "7D4FA370-30F2-4F59-9E3C-6D53C01DAA7C",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-02-18",
        "end": "2019-03-01",
        "idModule": 34,
        "idClasses": "3AB4CDEA-A36F-426F-87D1-AA7F45BAF6B2",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-03-04",
        "end": "2019-03-08",
        "idModule": 731,
        "idClasses": "FABB62A4-66D5-458C-846C-E6C8927249DC",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-04-01",
        "end": "2019-04-19",
        "idModule": 727,
        "idClasses": "3E17AF8A-FC24-4BDA-8010-0203826B9899",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-05-27",
        "end": "2019-05-31",
        "idModule": 36,
        "idClasses": "01CF703D-66AD-413B-8940-46BF4684F885",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-07-01",
        "end": "2019-07-19",
        "idModule": 560,
        "idClasses": "4EBD9C3F-373D-468D-BE7C-0C54BB88410C",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-09-16",
        "end": "2019-09-20",
        "idModule": 566,
        "idClasses": "EBD828B0-4CF0-464D-876C-929B8A5C8E80",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-11-19",
        "end": "2019-11-20",
        "idModule": 817,
        "idClasses": "2DDD0DDF-FB7B-4C16-8C36-6C23B776FD86",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      }
    ]
  },
  {
    "constraint": [
      {
        "priority": 8,
        "value": {
          "maxWeekInTraining": 3,
          "minWeekInCompany": 3
        },
        "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
        "respected": false
      },
      {
        "priority": 7,
        "value": 10,
        "name": "Lieu",
        "respected": false
      },
      {
        "priority": 5,
        "value": {
          "start": "2018-05-21",
          "end": "2018-05-27"
        },
        "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
        "respected": true
      },
      {
        "priority": 4,
        "value": {
          "start": "2018-04-02",
          "end": "2018-04-06"
        },
        "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
        "respected": true
      },
      {
        "priority": -1,
        "value": true,
        "name": "Modules prérequis",
        "respected": true
      }
    ],
    "cours": [
      {
        "start": "2018-03-26",
        "end": "2018-03-30",
        "idModule": 20,
        "idClasses": "9AC9F5B9-BE0F-418D-AC3C-00EBB8582246",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-03",
        "end": "2018-04-06",
        "idModule": 21,
        "idClasses": "46DB575D-76BB-40EC-BEBA-E408F4EE158B",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-09",
        "end": "2018-04-13",
        "idModule": 708,
        "idClasses": "88EED523-8B7C-4F5C-B71A-3D29C589FDB8",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-30",
        "end": "2018-05-04",
        "idModule": 725,
        "idClasses": "9AF796A9-ABF2-45EC-8068-F616B71A01D6",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-06-04",
        "end": "2018-06-15",
        "idModule": 734,
        "idClasses": "D820AA47-D8B8-47AA-91DE-D864EB65C4EA",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-06-18",
        "end": "2018-06-22",
        "idModule": 541,
        "idClasses": "3616294E-D6BA-484F-AE40-B8DEF50FFA77",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-07-16",
        "end": "2018-07-27",
        "idModule": 302,
        "idClasses": "A6F82AF0-CAB6-42FB-A1B2-56B7A7ED1CEF",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-08-27",
        "end": "2018-09-07",
        "idModule": 728,
        "idClasses": "98B4405A-1760-4C4D-A131-1F23D754E51F",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-09-10",
        "end": "2018-09-14",
        "idModule": 729,
        "idClasses": "CAB51343-77CE-4A05-9A27-75BDA17AFAAD",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-12-10",
        "end": "2018-12-21",
        "idModule": 544,
        "idClasses": "966E9CF6-1126-474A-A8CC-D01EA99E3735",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-01-14",
        "end": "2019-01-25",
        "idModule": 730,
        "idClasses": "7D4FA370-30F2-4F59-9E3C-6D53C01DAA7C",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-02-18",
        "end": "2019-03-01",
        "idModule": 34,
        "idClasses": "3AB4CDEA-A36F-426F-87D1-AA7F45BAF6B2",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-03-04",
        "end": "2019-03-08",
        "idModule": 731,
        "idClasses": "FABB62A4-66D5-458C-846C-E6C8927249DC",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-04-01",
        "end": "2019-04-19",
        "idModule": 727,
        "idClasses": "3E17AF8A-FC24-4BDA-8010-0203826B9899",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-05-27",
        "end": "2019-05-31",
        "idModule": 36,
        "idClasses": "01CF703D-66AD-413B-8940-46BF4684F885",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-07-01",
        "end": "2019-07-19",
        "idModule": 560,
        "idClasses": "4EBD9C3F-373D-468D-BE7C-0C54BB88410C",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-09-16",
        "end": "2019-09-20",
        "idModule": 566,
        "idClasses": "EBD828B0-4CF0-464D-876C-929B8A5C8E80",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-11-19",
        "end": "2019-11-20",
        "idModule": 817,
        "idClasses": "2DDD0DDF-FB7B-4C16-8C36-6C23B776FD86",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      }
    ]
  },
  {
    "constraint": [
      {
        "priority": 8,
        "value": {
          "maxWeekInTraining": 3,
          "minWeekInCompany": 3
        },
        "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
        "respected": false
      },
      {
        "priority": 7,
        "value": 10,
        "name": "Lieu",
        "respected": false
      },
      {
        "priority": 5,
        "value": {
          "start": "2018-05-21",
          "end": "2018-05-27"
        },
        "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
        "respected": true
      },
      {
        "priority": 4,
        "value": {
          "start": "2018-04-02",
          "end": "2018-04-06"
        },
        "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
        "respected": true
      },
      {
        "priority": -1,
        "value": true,
        "name": "Modules prérequis",
        "respected": true
      }
    ],
    "cours": [
      {
        "start": "2018-03-26",
        "end": "2018-03-30",
        "idModule": 20,
        "idClasses": "EEF20121-7745-4C55-8259-06ED9888E0A4",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-03",
        "end": "2018-04-06",
        "idModule": 21,
        "idClasses": "46DB575D-76BB-40EC-BEBA-E408F4EE158B",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-09",
        "end": "2018-04-13",
        "idModule": 708,
        "idClasses": "88EED523-8B7C-4F5C-B71A-3D29C589FDB8",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-04-30",
        "end": "2018-05-04",
        "idModule": 725,
        "idClasses": "9AF796A9-ABF2-45EC-8068-F616B71A01D6",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-05-28",
        "end": "2018-06-01",
        "idModule": 541,
        "idClasses": "219CC390-7A58-477F-83D8-75278977BC67",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-06-04",
        "end": "2018-06-15",
        "idModule": 734,
        "idClasses": "D820AA47-D8B8-47AA-91DE-D864EB65C4EA",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-07-16",
        "end": "2018-07-27",
        "idModule": 302,
        "idClasses": "A6F82AF0-CAB6-42FB-A1B2-56B7A7ED1CEF",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-08-27",
        "end": "2018-09-07",
        "idModule": 728,
        "idClasses": "98B4405A-1760-4C4D-A131-1F23D754E51F",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-09-10",
        "end": "2018-09-14",
        "idModule": 729,
        "idClasses": "CAB51343-77CE-4A05-9A27-75BDA17AFAAD",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2018-12-10",
        "end": "2018-12-21",
        "idModule": 544,
        "idClasses": "966E9CF6-1126-474A-A8CC-D01EA99E3735",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-01-14",
        "end": "2019-01-25",
        "idModule": 730,
        "idClasses": "7D4FA370-30F2-4F59-9E3C-6D53C01DAA7C",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": false
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-02-18",
        "end": "2019-03-01",
        "idModule": 34,
        "idClasses": "3AB4CDEA-A36F-426F-87D1-AA7F45BAF6B2",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-03-04",
        "end": "2019-03-08",
        "idModule": 731,
        "idClasses": "FABB62A4-66D5-458C-846C-E6C8927249DC",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-04-01",
        "end": "2019-04-19",
        "idModule": 727,
        "idClasses": "3E17AF8A-FC24-4BDA-8010-0203826B9899",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": true
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-05-27",
        "end": "2019-05-31",
        "idModule": 36,
        "idClasses": "01CF703D-66AD-413B-8940-46BF4684F885",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-07-01",
        "end": "2019-07-19",
        "idModule": 560,
        "idClasses": "4EBD9C3F-373D-468D-BE7C-0C54BB88410C",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-09-16",
        "end": "2019-09-20",
        "idModule": 566,
        "idClasses": "EBD828B0-4CF0-464D-876C-929B8A5C8E80",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      },
      {
        "start": "2019-11-19",
        "end": "2019-11-20",
        "idModule": 817,
        "idClasses": "2DDD0DDF-FB7B-4C16-8C36-6C23B776FD86",
        "constraints": [
          {
            "priority": 8,
            "value": {
              "maxWeekInTraining": 3,
              "minWeekInCompany": 3
            },
            "name": "Fréquence de formation de 3 semaines et de 3 semaines en entreprise",
            "respected": true
          },
          {
            "priority": 7,
            "value": 10,
            "name": "Lieu",
            "respected": false
          },
          {
            "priority": 5,
            "value": {
              "start": "2018-05-21",
              "end": "2018-05-27"
            },
            "name": "Périodes exclues du 2018-05-21 au 2018-05-27",
            "respected": true
          },
          {
            "priority": 4,
            "value": {
              "start": "2018-04-02",
              "end": "2018-04-06"
            },
            "name": "Périodes inclues du 2018-04-02 au 2018-04-06",
            "respected": true
          },
          {
            "priority": -1,
            "value": true,
            "name": "Modules prérequis",
            "respected": true
          }
        ]
      }
    ]
  }
]
```

