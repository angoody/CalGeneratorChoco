package solver;

import models.*;

import java.util.*;


public class test {

    public static List<Calendrier> main (String[] args){


        Probleme probleme = initProblem();



        ChocoSolver solver = new ChocoSolver(probleme);
        solver.addListener(new ChocoSolverListener() {


            @Override
            public void foundCalendar(Calendrier calendrier) {

                //calendrier.getCours().forEach(c -> afficheCours(c));

            }

            @Override
            public void foundCours(Cours cours) {
                //System.out.println(cours.getPeriode().getDebut()+ " " + cours.getPeriode().getFin() + " " + cours.getIdCours());
            }

            @Override
            public void finish() {
                System.out.println("Finit");
            }
        });
        List<Calendrier> calendriers = solver.resoudre(5);

        compare (calendriers);
        return calendriers;

    }

    private static void compare(List<Calendrier> calendriers) {
        for (Calendrier calendrier:calendriers) {
            List <Calendrier> autresCalendriers = new ArrayList<>(calendriers);
            autresCalendriers.remove(calendrier);
            autresCalendriers.stream().filter(cal -> cal.getCours().containsAll(calendrier.getCours())).forEach(c -> System.out.println("Doublon trouvé"));

        }
    }


    private static Probleme initProblem() {
        Probleme probleme = new Probleme();
        List<Module> modules = new ArrayList<Module>();


        // SELECT 'Module m' + REPLACE(m.LibelleCourt, '-', '') + ' = new Module (' +  CAST(m.IdModule AS VARCHAR(10) ) +', new HashSet<Module>(), new TreeSet<Cours>());' from module m left join ModuleParUnite mpu on m.IdModule = mpu.IdModule left join UniteParFormation upf on mpu.IdUnite = upf.Id where upf.CodeFormation = '17CDI   '
        Module mSTAGECDI2 = new Module(304, new HashSet<Module>(), new TreeSet<Cours>(), 2, 70);
        Module m16DLPOOJ = new Module(708, new HashSet<Module>(), new TreeSet<Cours>(), 3, 105);
        Module mSQL = new Module(20, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mPLSQL = new Module(21, new HashSet<Module>(), new TreeSet<Cours>(), 2, 35);
        Module mJAVA1DL17 = new Module(725, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mPRJ1DEV17 = new Module(730, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mDVWEBCL = new Module(541, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mDVWEBPH = new Module(544, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mJAVA2 = new Module(302, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mPRJ2DEV17 = new Module(727, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mMOB1DEV17 = new Module(728, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mPRJ3DEV17 = new Module(729, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mJ2EAV = new Module(34, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mAPACCDI17 = new Module(731, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mCPCOURS = new Module(36, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mDVWEBASPX = new Module(560, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mXAMARIN = new Module(566, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module mCONCDEV17 = new Module(734, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);
        Module m17BILAN_FINAL = new Module(817, new HashSet<Module>(), new TreeSet<Cours>(), 1, 35);



        // SELECT 'm' + REPLACE(m.LibelleCourt, '-', '') + '.getCours().add(new Cours(new Periode("' + CONVERT(varchar, c.debut, 103) + ' 00:00:00","' + CONVERT(varchar, c.fin, 103) + ' 00:00:00", "dd/MM/yyyy HH:mm:ss"),"' + CAST(c.IdCours AS VARCHAR(40)) + '",' + CAST (m.IdModule AS VARCHAR(10))+ ','+ CAST (c.CodeLieu AS VARCHAR(10)) +'));' from cours c left join module m on c.IdModule = m.IdModule left join ModuleParUnite mpu on m.IdModule = mpu.IdModule left join UniteParFormation upf on mpu.IdUnite = upf.Id where upf.CodeFormation = '17CDI   '  order by m.IdModule
        mSQL.getCours().add(new Cours(new Periode("26/03/2018 00:00:00","30/03/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9AC9F5B9-BE0F-418D-AC3C-00EBB8582246",20,11, 35));
        mSQL.getCours().add(new Cours(new Periode("26/03/2018 00:00:00","30/03/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"EEF20121-7745-4C55-8259-06ED9888E0A4",20,2, 35));
        mSQL.getCours().add(new Cours(new Periode("16/04/2018 00:00:00","20/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"20D8C8D2-2FC7-4922-A4C6-1CAFF10C15D4",20,1, 35));
        mSQL.getCours().add(new Cours(new Periode("16/04/2018 00:00:00","20/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"7CCFF5C3-EEC0-486B-ADF9-1FAD5977D3B9",20,1, 35));
        mSQL.getCours().add(new Cours(new Periode("16/04/2018 00:00:00","20/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"49384010-C777-47F7-8BAF-25E8271AC403",20,2, 35));
        mSQL.getCours().add(new Cours(new Periode("18/06/2018 00:00:00","22/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"13FDBD8F-8F18-4A41-8B76-2F63CDF62FC5",20,1, 35));
        mSQL.getCours().add(new Cours(new Periode("16/04/2018 00:00:00","20/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F02282BF-C22E-440B-BA13-31B6DABF19F5",20,10, 35));
        mSQL.getCours().add(new Cours(new Periode("10/12/2018 00:00:00","14/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"A7F50F72-8930-4C01-93F7-32388A4B91E2",20,10, 35));
        mSQL.getCours().add(new Cours(new Periode("08/10/2018 00:00:00","12/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"3F2BA010-10EA-412E-BF94-33E25B078A46",20,1, 35));
        mSQL.getCours().add(new Cours(new Periode("22/01/2018 00:00:00","26/01/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"71E6F84D-928F-41AE-B456-4321A8848462",20,2, 35));
        mSQL.getCours().add(new Cours(new Periode("18/06/2018 00:00:00","22/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"5D74CB18-FB70-4412-823C-56B26A3B123F",20,11, 35));
        mSQL.getCours().add(new Cours(new Periode("03/12/2018 00:00:00","07/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B2165BF1-133F-4502-A621-6683E9929885",20,1, 35));
        mSQL.getCours().add(new Cours(new Periode("05/02/2018 00:00:00","09/02/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"A38899A4-DC5A-4B86-8FC6-8F7285513726",20,12, 35));
        mSQL.getCours().add(new Cours(new Periode("08/10/2018 00:00:00","12/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"BC2762CC-2544-4D01-8B47-B25DC6C07B12",20,1, 35));
        mSQL.getCours().add(new Cours(new Periode("02/01/2018 00:00:00","05/01/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"263AAA5E-994E-4D2B-AEFA-EED5E6B2639C",20,1, 35));
        mPLSQL.getCours().add(new Cours(new Periode("03/04/2018 00:00:00","06/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"46DB575D-76BB-40EC-BEBA-E408F4EE158B",21,11, 35));
        mPLSQL.getCours().add(new Cours(new Periode("15/10/2018 00:00:00","19/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"69D4F236-9A75-41C5-B103-ED63FABDDFE2",21,1, 35));
        mPLSQL.getCours().add(new Cours(new Periode("23/04/2018 00:00:00","27/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B0BEE6FA-0DB7-4C3B-BAC1-B989FB149FD7",21,10, 35));
        mPLSQL.getCours().add(new Cours(new Periode("23/04/2018 00:00:00","27/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"188C569B-8C57-4EA2-9A1C-BD1A422C675E",21,2, 35));
        mPLSQL.getCours().add(new Cours(new Periode("15/10/2018 00:00:00","19/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"427EA863-2410-47C1-9825-B15DEB1BC565",21,1, 35));
        mPLSQL.getCours().add(new Cours(new Periode("12/02/2018 00:00:00","16/02/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"E286D7DC-EA10-4FBD-8A0A-C9C25919ADE0",21,12, 35));
        mPLSQL.getCours().add(new Cours(new Periode("29/01/2018 00:00:00","02/02/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"7A164585-9AC9-4B8F-9177-D19F14D8FECE",21,2, 35));
        mPLSQL.getCours().add(new Cours(new Periode("17/12/2018 00:00:00","21/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C3AFAF49-73E8-4737-AA7A-8EEC81927D16",21,10, 35));
        mPLSQL.getCours().add(new Cours(new Periode("10/12/2018 00:00:00","14/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F4F54845-1C84-4EB7-8F6A-5FD020B54B01",21,1, 35));
        mPLSQL.getCours().add(new Cours(new Periode("03/04/2018 00:00:00","06/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C73EEC8A-481A-487A-A896-7E77C8A78F5D",21,2, 35));
        mPLSQL.getCours().add(new Cours(new Periode("23/04/2018 00:00:00","27/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C5E55386-6F6F-4D49-BDB6-4661BCFD745F",21,1, 35));
        mPLSQL.getCours().add(new Cours(new Periode("23/04/2018 00:00:00","27/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F15DB3EA-1AA8-4D13-8B96-48C3E58CE504",21,1, 35));
        mPLSQL.getCours().add(new Cours(new Periode("25/06/2018 00:00:00","29/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"602DC652-F338-4AB7-83C3-249FDFD49AD2",21,1, 35));
        mPLSQL.getCours().add(new Cours(new Periode("25/06/2018 00:00:00","29/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"7F4050E5-2B0A-4EAD-9847-080E8207296A",21,11, 35));
        mJ2EAV.getCours().add(new Cours(new Periode("30/07/2018 00:00:00","10/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"29C7B5D7-494E-45F2-A4B5-0BF69055A08E",34,2, 35));
        mJ2EAV.getCours().add(new Cours(new Periode("08/04/2019 00:00:00","19/04/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"079857A4-C961-4CA5-A3F9-0F7697F379D1",34,2, 35));
        mJ2EAV.getCours().add(new Cours(new Periode("22/10/2018 00:00:00","02/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F1D989DA-24A7-4D62-A248-2A6615F93994",34,11, 35));
        mJ2EAV.getCours().add(new Cours(new Periode("27/08/2018 00:00:00","07/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"A140AE65-7FC8-4F93-AEE8-38A9D349EC19",34,1, 35));
        mJ2EAV.getCours().add(new Cours(new Periode("18/02/2019 00:00:00","01/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"3AB4CDEA-A36F-426F-87D1-AA7F45BAF6B2",34,1, 35));
        mJ2EAV.getCours().add(new Cours(new Periode("15/04/2019 00:00:00","26/04/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"217BF397-CB75-430C-AE2A-FF76AEF2196F",34,1, 35));
        mCPCOURS.getCours().add(new Cours(new Periode("12/11/2018 00:00:00","16/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"7487CDF9-CD97-44A6-8ECF-F9DF66748166",36,11, 35));
        mCPCOURS.getCours().add(new Cours(new Periode("06/05/2019 00:00:00","10/05/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"748346CF-194A-4F26-9C97-ED3790BF85C8",36,1, 35));
        mCPCOURS.getCours().add(new Cours(new Periode("11/03/2019 00:00:00","15/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9D8B2B45-4750-4272-AF0F-718E62F037D4",36,1, 35));
        mCPCOURS.getCours().add(new Cours(new Periode("17/09/2018 00:00:00","21/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"993F8A4D-CBA5-412C-9966-3F3630F630E9",36,1, 35));
        mCPCOURS.getCours().add(new Cours(new Periode("20/08/2018 00:00:00","24/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"3801F745-4FED-4E05-B9A6-4998DBC7C480",36,2, 35));
        mCPCOURS.getCours().add(new Cours(new Periode("27/05/2019 00:00:00","31/05/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"01CF703D-66AD-413B-8940-46BF4684F885",36,2, 35));
        mJAVA2.getCours().add(new Cours(new Periode("11/03/2019 00:00:00","22/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"1F2053DA-D71F-4D3A-8183-466B19E34B39",302,1, 35));
        mJAVA2.getCours().add(new Cours(new Periode("16/07/2018 00:00:00","27/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"A6F82AF0-CAB6-42FB-A1B2-56B7A7ED1CEF",302,10, 35));
        mJAVA2.getCours().add(new Cours(new Periode("14/01/2019 00:00:00","25/01/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"601CC889-8343-4E62-89AE-540022CDF3F4",302,1, 35));
        mJAVA2.getCours().add(new Cours(new Periode("17/09/2018 00:00:00","28/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"CB4DD72B-0C0E-45E7-932E-3A1D75F9841C",302,1, 35));
        mJAVA2.getCours().add(new Cours(new Periode("29/01/2018 00:00:00","09/02/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"86BD5A87-8796-4841-96C2-30999B3ABE7A",302,1, 35));
        mJAVA2.getCours().add(new Cours(new Periode("25/06/2018 00:00:00","06/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C90FF4CC-8847-4895-A0F4-074717D248D1",302,11, 35));
        mJAVA2.getCours().add(new Cours(new Periode("18/03/2019 00:00:00","29/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"00CB9CE4-0DEA-47A3-95B4-717EB14BA7EB",302,10, 35));
        mJAVA2.getCours().add(new Cours(new Periode("16/07/2018 00:00:00","27/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"854AB776-5894-4CEB-BE4B-A6702A2F1398",302,1, 35));
        mJAVA2.getCours().add(new Cours(new Periode("07/01/2019 00:00:00","18/01/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C2B13BEE-AF8C-4B01-9138-96472CED391C",302,2, 35));
        mJAVA2.getCours().add(new Cours(new Periode("30/04/2018 00:00:00","04/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"22D0DC71-08F4-4FFD-BC5A-8E5F9DD1F65C",302,12, 35));
        mJAVA2.getCours().add(new Cours(new Periode("16/07/2018 00:00:00","27/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9C159163-395C-4C42-8A55-8205C8A0AADD",302,1, 35));
        mJAVA2.getCours().add(new Cours(new Periode("14/05/2018 00:00:00","18/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"AE328890-2F0D-4159-9E8F-83614E398E29",302,12, 35));
        mJAVA2.getCours().add(new Cours(new Periode("14/01/2019 00:00:00","25/01/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"91A46A5E-F1B3-41BF-8B89-E56D82E1952D",302,1, 35));
        mJAVA2.getCours().add(new Cours(new Periode("25/06/2018 00:00:00","06/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"2719B3A2-AB19-49C5-8BF2-FDB79C75A0E6",302,2, 35));
        mJAVA2.getCours().add(new Cours(new Periode("17/09/2018 00:00:00","28/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B9ACABF3-51FA-460B-9016-FE647A831F2D",302,11, 35));
        mSTAGECDI2.getCours().add(new Cours(new Periode("21/01/2019 00:00:00","15/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"A29FEEB6-4CD9-4F97-9922-F37C5315340C",304,11, 35));
        mSTAGECDI2.getCours().add(new Cours(new Periode("06/05/2019 00:00:00","28/06/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"D774AAB9-22F8-4747-BC50-CE108A7CEB56",304,1, 35));
        mSTAGECDI2.getCours().add(new Cours(new Periode("01/07/2019 00:00:00","23/08/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"93549FDE-A757-4630-8F3D-93DCD6154C73",304,1, 35));
        mSTAGECDI2.getCours().add(new Cours(new Periode("12/11/2018 00:00:00","21/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"1B040E3B-F71D-4A51-B408-7348746C1A9C",304,1, 35));
        mSTAGECDI2.getCours().add(new Cours(new Periode("15/10/2018 00:00:00","07/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"3E9B3F7A-7493-49EC-B490-5F5A98EAE186",304,2, 35));
        mSTAGECDI2.getCours().add(new Cours(new Periode("02/01/2019 00:00:00","11/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"10634E06-A6D8-4FE6-8500-1CD51D27DC13",304,1, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("03/12/2018 00:00:00","07/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"7F6F1BEA-4AD3-409F-B7D4-1D8233BB72F9",541,1, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("18/06/2018 00:00:00","22/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"1EFC4886-2EE8-4526-84CF-1DC2FCD3246F",541,10, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("20/08/2018 00:00:00","24/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B050CD0E-2390-45C0-918B-1EACB3EFF211",541,1, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("20/08/2018 00:00:00","24/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"CDF20F3C-94D4-4069-A640-00E3100FDAE1",541,11, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("28/05/2018 00:00:00","01/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"DF59BE30-CBE4-4694-8470-5715606FE9C9",541,11, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("28/05/2018 00:00:00","01/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"219CC390-7A58-477F-83D8-75278977BC67",541,2, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("03/04/2018 00:00:00","06/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"3C6B1394-78A3-4EE8-A23D-85BABF0D24E2",541,12, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("18/06/2018 00:00:00","22/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B41E4437-5145-498A-A1C4-8DF1BE253365",541,1, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("18/02/2019 00:00:00","22/02/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9CD9F784-76BC-4BA3-AA82-CABB25ACA379",541,10, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("18/06/2018 00:00:00","22/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"3616294E-D6BA-484F-AE40-B8DEF50FFA77",541,1, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("11/02/2019 00:00:00","15/02/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"EC7AECCD-A8EE-436C-B9B5-D20A816744B7",541,1, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("08/10/2018 00:00:00","12/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B061528A-4125-400C-A348-BDDA7469D42F",541,2, 35));
        mDVWEBCL.getCours().add(new Cours(new Periode("03/12/2018 00:00:00","07/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"D7D0B8AD-841E-4E77-9951-EFBD2EE80481",541,1, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("25/06/2018 00:00:00","13/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"EDBF0441-50CC-4B38-A0C0-E0A79C95E2E9",544,10, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("27/08/2018 00:00:00","14/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"2C2B0501-487C-46F7-A98C-E1549BD944F5",544,11, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("25/02/2019 00:00:00","15/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C1F8614E-DA58-4620-A20E-E6B6461D746B",544,10, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("07/01/2019 00:00:00","11/01/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"3145CAFA-0E4B-420A-8265-B8143624B40B",544,1, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("10/12/2018 00:00:00","21/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"966E9CF6-1126-474A-A8CC-D01EA99E3735",544,1, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("18/02/2019 00:00:00","08/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"E3BE7F4B-A4B3-4675-80BA-87381D26EBC1",544,1, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("04/06/2018 00:00:00","22/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"E9AA5E81-77B0-43DB-B2F2-A77F62D16933",544,11, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("25/06/2018 00:00:00","13/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"AD33FBE0-C07F-4737-9C2B-7ED85F26805C",544,1, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("07/01/2019 00:00:00","11/01/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"1B147CB2-F7DC-45E4-BDD6-6BDF029A362D",544,1, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("10/12/2018 00:00:00","21/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"A8FD9341-5439-4C7E-AFF4-6966F14F5BC0",544,1, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("04/06/2018 00:00:00","22/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"EB2ACA76-6421-4514-A1B8-5C1781B98609",544,2, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("19/11/2018 00:00:00","30/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"0B839480-E37D-48D8-BABB-55B61ED78431",544,2, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("27/08/2018 00:00:00","14/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"934042C9-1EF3-4B64-9F25-4D153C54042F",544,1, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("25/06/2018 00:00:00","13/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"E9093CE0-CFFA-4962-8276-442B4F3FA86C",544,1, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("15/10/2018 00:00:00","19/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"AB2AE0F6-793C-4DB9-9701-297506E39300",544,2, 35));
        mDVWEBPH.getCours().add(new Cours(new Periode("09/04/2018 00:00:00","27/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"85479554-E63F-44D0-81A9-0BD66A5F262D",544,12, 35));
        mDVWEBASPX.getCours().add(new Cours(new Periode("01/07/2019 00:00:00","19/07/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"4EBD9C3F-373D-468D-BE7C-0C54BB88410C",560,2, 35));
        mDVWEBASPX.getCours().add(new Cours(new Periode("18/03/2019 00:00:00","05/04/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"AD6EFD1D-80DA-4063-8D3B-411954AB3F32",560,1, 35));
        mDVWEBASPX.getCours().add(new Cours(new Periode("19/11/2018 00:00:00","07/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"59B0F1DC-DAEB-4BF4-BB81-484F3919F66B",560,11, 35));
        mDVWEBASPX.getCours().add(new Cours(new Periode("24/09/2018 00:00:00","12/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"75EB593F-DF78-4A14-848B-5313A4BE520B",560,1, 35));
        mDVWEBASPX.getCours().add(new Cours(new Periode("27/08/2018 00:00:00","14/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"CFC787BF-FE59-4FE7-8E4F-7C2AA5C47CAE",560,2, 35));
        mDVWEBASPX.getCours().add(new Cours(new Periode("13/05/2019 00:00:00","31/05/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B4B5E257-3F55-4555-87EE-8F32F5B5DC3A",560,1, 35));
        mXAMARIN.getCours().add(new Cours(new Periode("05/11/2018 00:00:00","09/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"D9E80C62-D60F-4D41-A938-80C44A7FFAE4",566,1, 35));
        mXAMARIN.getCours().add(new Cours(new Periode("14/01/2019 00:00:00","18/01/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"EA4755F1-3C0E-43D7-9326-A1C999F30E7D",566,11, 35));
        mXAMARIN.getCours().add(new Cours(new Periode("24/06/2019 00:00:00","28/06/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"174DEC07-CCB1-482F-8CE3-910DDA1F7467",566,1, 35));
        mXAMARIN.getCours().add(new Cours(new Periode("16/09/2019 00:00:00","20/09/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"EBD828B0-4CF0-464D-876C-929B8A5C8E80",566,2, 35));
        mXAMARIN.getCours().add(new Cours(new Periode("21/10/2019 00:00:00","25/10/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"2AD6370A-5C01-49E7-9342-5EC4DF37783D",566,2, 35));
        mXAMARIN.getCours().add(new Cours(new Periode("08/10/2018 00:00:00","12/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"CA3B48C2-2729-489D-9BC5-33225F26337B",566,2, 35));
        mXAMARIN.getCours().add(new Cours(new Periode("29/04/2019 00:00:00","03/05/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"D3E9EE90-4BDB-432B-823E-DEA0FA2B1B9D",566,1, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("26/11/2018 00:00:00","30/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"D090B325-1AB8-48D6-99C5-E0585D14D05A",708,1, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("19/03/2018 00:00:00","23/03/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"8DCF25E3-1B22-47AF-9400-C891A50240A3",708,2, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("11/06/2018 00:00:00","15/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"8FF17DC2-3AE1-4E39-BCDE-B115D6536F61",708,11, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("19/03/2018 00:00:00","23/03/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"91163186-74AE-4458-8B12-E5106F8DC105",708,11, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("03/12/2018 00:00:00","07/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"23C7F6A7-D917-4C3A-84A7-1FFEA8F83016",708,10, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("09/04/2018 00:00:00","13/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9D39D0F3-395F-4C02-8DCF-222934035F4A",708,1, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("29/01/2018 00:00:00","02/02/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"2BCA7AFD-9A08-479C-B1BA-0B09CD4DF4A6",708,12, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("01/10/2018 00:00:00","05/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9004BC97-0BBA-4CDA-BB17-439B7C6B3025",708,1, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("09/04/2018 00:00:00","13/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"88EED523-8B7C-4F5C-B71A-3D29C589FDB8",708,10, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("11/12/2017 00:00:00","15/12/2017 00:00:00", "dd/MM/yyyy HH:mm:ss"),"77D66D8A-DEFD-44F6-A158-65DD5A34400E",708,2, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("09/04/2018 00:00:00","13/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"1EB21C6B-7E5F-4921-A3AB-6D64053DA378",708,1, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("11/06/2018 00:00:00","15/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C24C868C-1BD2-4FC8-89D4-6E18F47F23C0",708,1, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("18/12/2017 00:00:00","22/12/2017 00:00:00", "dd/MM/yyyy HH:mm:ss"),"8197E1DD-1169-4E74-82CF-A0576081D58D",708,1, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("09/04/2018 00:00:00","13/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"27C4662D-83FD-4A38-B3B3-AA8CB7F292ED",708,2, 35));
        m16DLPOOJ.getCours().add(new Cours(new Periode("01/10/2018 00:00:00","05/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"3AA758F1-2C0A-4570-AA3F-85F84117E429",708,1, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("14/05/2018 00:00:00","25/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"79125B83-EF81-4387-AF74-7FDFDF86E8A8",725,2, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("09/04/2018 00:00:00","20/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"85239D35-A51E-4430-8B4F-8B05E5159614",725,2, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("17/12/2018 00:00:00","21/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"A213824B-6763-4780-AC44-72A3B327D137",725,1, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("14/05/2018 00:00:00","18/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"7C6D3F41-9EA3-41B9-98F5-5A2D5955E2CE",725,1, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("14/05/2018 00:00:00","18/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"82B76FAB-C89C-41A7-AC7F-3ECFD6E71643",725,1, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("02/07/2018 00:00:00","13/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F818424D-09CA-4E5A-A405-42DDEEBF1B64",725,1, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("22/10/2018 00:00:00","02/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"BD621130-84B8-4BA6-9EBA-4D75AE05684B",725,1, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("30/04/2018 00:00:00","04/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"77E51E57-4DC7-4C03-95F2-52D8FFDC37B2",725,10, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("07/01/2019 00:00:00","18/01/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"7F8F6DB0-54A7-4E74-A2FC-58A96E11F60E",725,10, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("19/02/2018 00:00:00","02/03/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"1D13DBC1-8AF0-4293-9BE0-19F53994F350",725,12, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("02/07/2018 00:00:00","13/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C2D24013-85C0-4661-A498-23D2F8CAA962",725,11, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("09/04/2018 00:00:00","20/04/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B887EFE6-DCD8-4473-8615-E660E5A64FC0",725,11, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("30/04/2018 00:00:00","04/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9AF796A9-ABF2-45EC-8068-F616B71A01D6",725,1, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("30/04/2018 00:00:00","04/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"87C761B2-D842-4182-B044-FBD5E7AB0647",725,1, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("07/01/2019 00:00:00","11/01/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"FA99B569-3A8E-48FC-84E5-AE08661E90A4",725,1, 35));
        mJAVA1DL17.getCours().add(new Cours(new Periode("22/10/2018 00:00:00","02/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"FF2F4269-F3FF-40B4-928F-B266146EF73F",725,1, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("25/03/2019 00:00:00","12/04/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"03DFABF1-9405-499B-8F73-AC917ED5532D",727,1, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("28/01/2019 00:00:00","15/02/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"33A95D4A-3C4F-46AC-B107-ADF5BABD63DA",727,1, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("18/02/2019 00:00:00","08/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"16E23474-A904-4CEB-B0B4-C5EB9963698F",727,2, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("01/10/2018 00:00:00","19/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F10880DF-9F18-41B3-85E1-C77C5031AD4F",727,11, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("22/05/2018 00:00:00","08/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"E24C6E81-0971-4C93-A749-EB516155B848",727,12, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("20/08/2018 00:00:00","24/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"BEC4E65E-3A92-431A-AA83-27C3352B53F3",727,1, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("20/08/2018 00:00:00","24/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"6A4C51CD-529F-4B6A-B8C2-0E1A7A7FC51C",727,1, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("30/07/2018 00:00:00","10/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"FC681319-FD7B-4250-AD48-09075D0CC074",727,10, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("01/04/2019 00:00:00","19/04/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"3E17AF8A-FC24-4BDA-8010-0203826B9899",727,10, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("01/10/2018 00:00:00","19/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"6A739FAD-0733-45AE-AB87-5623600452B0",727,1, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("09/07/2018 00:00:00","27/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C649E589-F653-4704-BC22-446E44625734",727,11, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("30/07/2018 00:00:00","10/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"6075D1E3-FD17-453D-8B86-5B9B4CBFA5AC",727,1, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("09/07/2018 00:00:00","27/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"40C0A526-F3AB-4FEC-B730-8C3AF52CED7E",727,2, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("30/07/2018 00:00:00","10/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"4ECFA3E8-2D13-4BF2-A49A-8167936CF76E",727,1, 35));
        mPRJ2DEV17.getCours().add(new Cours(new Periode("28/01/2019 00:00:00","15/02/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"6C77C766-656F-4DAE-93E8-84F3E7E45BDB",727,1, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("03/06/2019 00:00:00","14/06/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"115E3C13-AA90-4D23-A8C9-9A8FCC942F4D",728,1, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("30/07/2018 00:00:00","10/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"D45D5334-127F-450D-A4D5-9E35D80FEC6F",728,11, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("18/02/2019 00:00:00","01/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"20E21241-4344-483B-BBA2-6B776FF9A5C7",728,1, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("27/08/2018 00:00:00","07/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"2F8FC23C-7DF0-48ED-B0B9-3C542E439217",728,1, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("23/04/2019 00:00:00","03/05/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"445A8790-B375-403F-8134-5646089D5A19",728,10, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("08/04/2019 00:00:00","19/04/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"4F947DFC-520B-421C-98E8-064181CE9270",728,1, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("15/10/2018 00:00:00","26/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"CDAFADCB-7FF8-4B8C-8DBE-1C3A57069C63",728,1, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("27/08/2018 00:00:00","07/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"98B4405A-1760-4C4D-A131-1F23D754E51F",728,10, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("10/12/2018 00:00:00","21/12/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"D8DC6F44-3BA8-4722-81F9-2F2B89BBD8FC",728,11, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("11/06/2018 00:00:00","22/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"446C385E-707F-4113-996D-DCF7DC16D216",728,12, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("26/08/2019 00:00:00","06/09/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9E10FA5B-8D32-4844-A86F-E4BD5133D1BC",728,2, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("17/09/2018 00:00:00","28/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"0950D373-0902-4784-94BC-BEC90A8BD01A",728,2, 35));
        mMOB1DEV17.getCours().add(new Cours(new Periode("22/10/2018 00:00:00","02/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F522EAF4-5A9B-4927-9919-B383A3116AB0",728,1, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("04/03/2019 00:00:00","08/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"5DE27295-322F-4A96-8DAC-C3DC0BAA19F9",729,1, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("29/10/2018 00:00:00","02/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C357AB39-F257-4720-A965-DB9FB923AEA8",729,1, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("06/05/2019 00:00:00","10/05/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"58E280BA-5356-4816-874F-E4FF4F5C8C22",729,10, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("17/06/2019 00:00:00","21/06/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F53B456F-CBE6-424C-9BAF-594CAB2190AE",729,1, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("01/10/2018 00:00:00","05/10/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"DEB4033D-5E7A-4785-9C74-4BB837C555A0",729,2, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("25/06/2018 00:00:00","29/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9A0EC673-1147-4DC7-BE4C-38D70CBC4D26",729,12, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("05/11/2018 00:00:00","09/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"A7EEED71-3F90-47B5-AB71-38F1442E834D",729,1, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("23/04/2019 00:00:00","26/04/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F20D680F-2940-48D8-92AA-695CD09E40B1",729,1, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("10/09/2018 00:00:00","14/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"CAB51343-77CE-4A05-9A27-75BDA17AFAAD",729,10, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("09/09/2019 00:00:00","13/09/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"1A53B31D-5197-4855-99BE-744677A5D04B",729,2, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("07/01/2019 00:00:00","11/01/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"1931AEFE-92B7-4E2E-AFD2-AAA03DC369A7",729,11, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("10/09/2018 00:00:00","14/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"A6ECA816-248F-4361-BEC0-9D9622BE6C1D",729,1, 35));
        mPRJ3DEV17.getCours().add(new Cours(new Periode("13/08/2018 00:00:00","17/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"1DB7E74B-FEB9-43B7-88F1-A1D7C149E512",729,11, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("23/04/2018 00:00:00","04/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"3025FE4D-6C48-429F-B140-9CF881B8DF11",730,2, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("21/01/2019 00:00:00","01/02/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"058B4374-63DC-4416-A2E9-948160022AC8",730,10, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("05/03/2018 00:00:00","16/03/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"460B6F26-60E3-46B1-B5DA-8222CA7E7508",730,12, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("25/06/2018 00:00:00","06/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F679E790-176B-4E64-9EE4-7DDA8F7CDE57",730,2, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("14/01/2019 00:00:00","25/01/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"7D4FA370-30F2-4F59-9E3C-6D53C01DAA7C",730,1, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("16/07/2018 00:00:00","27/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9D2AF640-BC7A-48D8-BB73-3890A44972E5",730,11, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("22/05/2018 00:00:00","01/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"AB448A09-D837-481D-A51E-FDCC1ADEEEDE",730,1, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("22/05/2018 00:00:00","01/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"8B4BF1A0-9E34-4ACC-AB03-FFEC69085EDA",730,10, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("05/11/2018 00:00:00","16/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"7D237DD2-4F1C-41DB-9B09-D233A5191A18",730,1, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("05/11/2018 00:00:00","16/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"5321A049-620F-41C5-B0B8-D85F07716661",730,1, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("22/05/2018 00:00:00","01/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F67D20B5-7972-433B-AE34-CB9CB30085CF",730,1, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("16/07/2018 00:00:00","27/07/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"E2528FDB-2DCB-49F5-BF42-CE4C3BD0E6DB",730,1, 35));
        mPRJ1DEV17.getCours().add(new Cours(new Periode("23/04/2018 00:00:00","04/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"FAFE4C71-4220-48EB-BEF9-C836483B77B6",730,11, 35));
        mAPACCDI17.getCours().add(new Cours(new Periode("10/09/2018 00:00:00","14/09/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"C9EAD594-44CE-4F79-88E2-D1B75D274942",731,1, 35));
        mAPACCDI17.getCours().add(new Cours(new Periode("04/03/2019 00:00:00","08/03/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"FABB62A4-66D5-458C-846C-E6C8927249DC",731,1, 35));
        mAPACCDI17.getCours().add(new Cours(new Periode("20/05/2019 00:00:00","24/05/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B68EA7BA-5871-4093-95C9-79B5CE0C6C71",731,2, 35));
        mAPACCDI17.getCours().add(new Cours(new Periode("05/11/2018 00:00:00","09/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"D7D4E588-3FC1-4F43-918E-823773D7BF57",731,11, 35));
        mAPACCDI17.getCours().add(new Cours(new Periode("29/04/2019 00:00:00","03/05/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"1C46C421-3EF5-4938-8C81-9E4F556A00D5",731,1, 35));
        mAPACCDI17.getCours().add(new Cours(new Periode("13/08/2018 00:00:00","17/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F6F85C5D-AD6F-47C1-BB5F-9FDC333DD6C4",731,2, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("05/03/2018 00:00:00","16/03/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B15F237E-0FA4-43F0-A647-85DB803BF6C5",734,1, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("30/07/2018 00:00:00","10/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"85A5E18B-58E8-4C25-B95D-7E832D88D557",734,1, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("19/11/2018 00:00:00","30/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"45AA1DB4-9BE1-4584-9D43-7E8F522D13AC",734,1, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("04/02/2019 00:00:00","15/02/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"BAFD1012-9E8C-4D8D-9775-74056AB7E3E9",734,10, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("14/05/2018 00:00:00","25/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"49C58519-D02B-4473-B0A5-3FA251FF6490",734,11, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("20/08/2018 00:00:00","31/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"F9509972-B829-47FC-80AD-1FDF4DB35936",734,2, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("04/06/2018 00:00:00","15/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"646789C9-51F0-4641-B6A3-26527F863A38",734,1, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("14/05/2018 00:00:00","25/05/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"54263645-E9D2-4825-879C-E81C1C3E4A81",734,2, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("19/11/2018 00:00:00","30/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"A6FE3680-0EDF-439B-B8F3-EABAE3F1F3B0",734,1, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("30/07/2018 00:00:00","10/08/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"84531CF4-AA01-4C59-8D34-E53A3B39F8DF",734,11, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("19/03/2018 00:00:00","30/03/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"DE0F7590-2F6D-42FE-9ED1-FCB2C6DB2B65",734,12, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("04/06/2018 00:00:00","15/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"9A34D0AF-C62D-4BAA-BBD6-CEAF67FFC7AE",734,1, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("04/06/2018 00:00:00","15/06/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"D820AA47-D8B8-47AA-91DE-D864EB65C4EA",734,10, 35));
        mCONCDEV17.getCours().add(new Cours(new Periode("28/01/2019 00:00:00","08/02/2019 00:00:00", "dd/MM/yyyy HH:mm:ss"),"B58AAB97-439E-4D58-9FE5-B392733F5E6B",734,1, 35));
        m17BILAN_FINAL.getCours().add(new Cours(new Periode("19/11/2018 00:00:00","20/11/2018 00:00:00", "dd/MM/yyyy HH:mm:ss"),"2CF00DDF-FB7B-4C16-8C36-6C23B776FD86",817,2, 35));

        modules.add(mSTAGECDI2);
        modules.add(m16DLPOOJ);
        modules.add(mSQL);
        modules.add(mPLSQL);
        modules.add(mJAVA1DL17);
        modules.add(mPRJ1DEV17);
        modules.add(mDVWEBCL);
        modules.add(mDVWEBPH);
        modules.add(mJAVA2);
        modules.add(mPRJ2DEV17);
        modules.add(mMOB1DEV17);
        modules.add(mPRJ3DEV17);
        modules.add(mJ2EAV);
        modules.add(mAPACCDI17);
        modules.add(mCPCOURS);
        modules.add(mDVWEBASPX);
        modules.add(mXAMARIN);
        modules.add(mCONCDEV17);
        modules.add(m17BILAN_FINAL);


        probleme.setModulesFormation(modules);
        probleme.setPeriodeFormation(new Periode("2018-01-02 00:00:00", "2019-03-11 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        probleme.setContraintes(
                Arrays.asList(
                        new Contrainte(

                                Arrays.asList(new Integer(2), new Integer(1)),
                                1500,
                                3000,
                                20,
                                Arrays.asList(new Periode("2018-03-19 00:00:00", "2018-03-23 00:00:00", "yyyy-MM-dd HH:mm:ss")),
                                Arrays.asList(),
                                3,
                                Arrays.asList(),
                                Arrays.asList()
                        )
                )
        );
        return probleme;
    }

}
