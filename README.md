# CalGeneratorBack

## Description du projet

Ce projet expose une API REST permettant la génération d'un calendrier par contraintes. Ce projet a été demandé par l'ENI Ecole Informatique Nantes.

## Documentation de l'API

### Solve

| Méthode | Route | Paramètre | Retour | Exemple | 
|---------|-------|-----------|------- |---------|
| Get | /solve | Objet Problem | Renvoi les calendriers répondant au problème | 1 |


Exemple des paramètres en entrée :

```json
[
 {
    "periodeFormation":{
       "debut":"2018-08-20",
       "fin":"2019-04-01"
    },
    "modulesFormation":[
       {
          "idModule":20,
          "prerequis":[
 
          ],
          "cours":[
             {
                "periode":{
                   "debut":"2018-12-10 00:00:00.0",
                   "fin":"2018-12-14 00:00:00.0"
                },
                "idCours":"A7F50F72-8930-4C01-93F7-32388A4B91E2",
                "idModule":20,
                "lieu":10,
                "nbHeureReel":35
             },
             {
                "periode":{
                   "debut":"2018-10-08 00:00:00.0",
                   "fin":"2018-10-12 00:00:00.0"
                },
                "idCours":"3F2BA010-10EA-412E-BF94-33E25B078A46",
                "idModule":20,
                "lieu":1,
                "nbHeureReel":35
             },
             {
                "periode":{
                   "debut":"2018-12-03 00:00:00.0",
                   "fin":"2018-12-07 00:00:00.0"
                },
                "idCours":"B2165BF1-133F-4502-A621-6683E9929885",
                "idModule":20,
                "lieu":1,
                "nbHeureReel":35
             },
             {
                "periode":{
                   "debut":"2018-10-08 00:00:00.0",
                   "fin":"2018-10-12 00:00:00.0"
                },
                "idCours":"BC2762CC-2544-4D01-8B47-B25DC6C07B12",
                "idModule":20,
                "lieu":1,
                "nbHeureReel":35
             }
          ],
          "nbSemainePrevu":1,
          "nbHeurePrevu":35
       },
       {
          "idModule":21,
          "prerequis":[
 
          ],
          "cours":[
             {
                "periode":{
                   "debut":"2018-12-10 00:00:00.0",
                   "fin":"2018-12-14 00:00:00.0"
                },
                "idCours":"F4F54845-1C84-4EB7-8F6A-5FD020B54B01",
                "idModule":21,
                "lieu":1,
                "nbHeureReel":35
             },
             {
                "periode":{
                   "debut":"2018-12-17 00:00:00.0",
                   "fin":"2018-12-21 00:00:00.0"
                },
                "idCours":"C3AFAF49-73E8-4737-AA7A-8EEC81927D16",
                "idModule":21,
                "lieu":10,
                "nbHeureReel":35
             },
             {
                "periode":{
                   "debut":"2018-10-15 00:00:00.0",
                   "fin":"2018-10-19 00:00:00.0"
                },
                "idCours":"427EA863-2410-47C1-9825-B15DEB1BC565",
                "idModule":21,
                "lieu":1,
                "nbHeureReel":35
             },
             {
                "periode":{
                   "debut":"2018-10-15 00:00:00.0",
                   "fin":"2018-10-19 00:00:00.0"
                },
                "idCours":"69D4F236-9A75-41C5-B103-ED63FABDDFE2",
                "idModule":21,
                "lieu":1,
                "nbHeureReel":35
             }
          ],
          "nbSemainePrevu":1,
          "nbHeurePrevu":35
       },
       {
          "idModule":817,
          "prerequis":[
 
          ],
          "cours":[
             {
                "periode":{
                   "debut":"2018-11-19 00:00:00.0",
                   "fin":"2018-11-20 00:00:00.0"
                },
                "idCours":"2CF00DDF-FB7B-4C16-8C36-6C23B776FD86",
                "idModule":817,
                "lieu":2,
                "nbHeureReel":14
             }
          ],
          "nbSemainePrevu":0,
          "nbHeurePrevu":14
       }
    ],
    "contraintes":[
 
    ]
 }
]
```

Exemple des paramètres en entrée :

```json
[
    {
        "contraintesResolus": [],
        "contrainteNonResolu": [],
        "cours": [
            "3F2BA010-10EA-412E-BF94-33E25B078A46",
            "F4F54845-1C84-4EB7-8F6A-5FD020B54B01",
            "F1D989DA-24A7-4D62-A248-2A6615F93994",
            "7487CDF9-CD97-44A6-8ECF-F9DF66748166",
            "CB4DD72B-0C0E-45E7-932E-3A1D75F9841C",
            "1B040E3B-F71D-4A51-B408-7348746C1A9C",
            "9CD9F784-76BC-4BA3-AA82-CABB25ACA379",
            "966E9CF6-1126-474A-A8CC-D01EA99E3735",
            "59B0F1DC-DAEB-4BF4-BB81-484F3919F66B",
            "D9E80C62-D60F-4D41-A938-80C44A7FFAE4",
            "23C7F6A7-D917-4C3A-84A7-1FFEA8F83016",
            "7F8F6DB0-54A7-4E74-A2FC-58A96E11F60E",
            "F10880DF-9F18-41B3-85E1-C77C5031AD4F",
            "F522EAF4-5A9B-4927-9919-B383A3116AB0",
            "CAB51343-77CE-4A05-9A27-75BDA17AFAAD",
            "058B4374-63DC-4416-A2E9-948160022AC8",
            "C9EAD594-44CE-4F79-88E2-D1B75D274942",
            "45AA1DB4-9BE1-4584-9D43-7E8F522D13AC",
            "2CF00DDF-FB7B-4C16-8C36-6C23B776FD86"
        ]
    }
]
```

