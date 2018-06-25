package solver;

import models.input.*;
import models.output.Calendar;
import models.output.ClassesCalendar;
import scala.reflect.internal.util.Origins;
import solver.modelChoco.CoursChoco;
import solver.modelChoco.PeriodeChoco;
import utils.DateTimeHelper;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Test
{

    public static List<Calendar> solve()
    {


        Problem problem = initProblem();


        ChocoSolver solver = new ChocoSolver(problem);
        solver.addListener(new ChocoSolverListener()
        {


            @Override
            public void foundCalendar(Calendar calendar)
            {
                for (ClassesCalendar classe:calendar.getCours()) {
                    Module module = problem.getModuleOfTraining().stream().filter(m -> m.getIdModule() == classe.getIdModule()).findFirst().get();
                    Classes c = module.getListClasses().stream().filter(m -> m.getIdClasses().contentEquals(classe.getIdClasses())).findFirst().get();

                    System.out.printf("Classes d'id %s du Module d'id %s > %s à %d le %s au %s (%d/%d)\n",
                            c.getIdClasses(),
                            module.getIdModule(),
                            String.join(",", module.getListIdModulePrerequisite().stream()
                                    .map(m -> String.valueOf(m))
                                    .collect(Collectors.toList())),
                            c.getIdPlace(),
                            c.getPeriod().getStart(),
                            c.getPeriod().getEnd(),
                            DateTimeHelper.toHourBetweenDateWithoutHolydays(c.getPeriod()),
                            module.getNbHourOfModule());
                }
            }

            @Override
            public void foundCours(ClassesCalendar cours)
            {

            }

            @Override
            public void finish()
            {
                System.out.println("Finit");
            }
        });
        List<Calendar> calendars = solver.solve(5);

        List<Classes> classes = problem.getModuleOfTraining().stream().flatMap(m -> m.getListClasses().stream()).collect(Collectors.toList());
        calendarToCsv(classes, calendars, problem.getPeriodOfTraining());
        compare(calendars);


        return calendars;

    }


    private void afficheCours(CoursChoco c) {


    }

    public static void calendarToCsv( List<Classes> classes, List<Calendar> calendars, Period period)
    {

        Map<Integer, String> modules = new HashMap<>();
        Map<Integer, String> lieux = new HashMap<>();

        modules.put(	20	,"	Le langage de requête SQL	");
        modules.put(	21	,"	Procédures stockées avec PL-SQL	");
        modules.put(	33	,"	Stage Développeur Logiciel	");
        modules.put(	34	,"	Utilisation de frameworks pour le développement avec Java EE	");
        modules.put(	36	,"	Conduite de projet	");
        modules.put(	44	,"	Base des réseaux	");
        modules.put(	45	,"	Utilisation et administration d'un système Windows Client	");
        modules.put(	51	,"	ITIL, gestion de parc et centre de support	");
        modules.put(	53	,"	TP services réseaux hétérogènes	");
        modules.put(	54	,"	Messagerie	");
        modules.put(	56	,"	TP et évaluations finales	");
        modules.put(	57	,"	Stage Informaticien Micro	");
        modules.put(	93	,"	Stage Administrateur Système et Réseau	");
        modules.put(	192	,"	Assistance bureautique	");
        modules.put(	269	,"	Utilisation et administration d'un système Linux	");
        modules.put(	270	,"	Services Réseaux Linux	");
        modules.put(	301	,"	Développement avec Java SE	");
        modules.put(	302	,"	Développement Web côté serveur avec Java EE	");
        modules.put(	304	,"	Stage CDI	");
        modules.put(	317	,"	Supervision avec Nagios	");
        modules.put(	344	,"	Services réseaux Windows	");
        modules.put(	345	,"	Déploiement des postes de travail sur un réseau d'entreprise	");
        modules.put(	346	,"	Administration d'un serveur applicatif	");
        modules.put(	347	,"	Virtualisation et clients légers	");
        modules.put(	421	,"	Bilan final	");
        modules.put(	477	,"	Supervision avec NAGIOS	");
        modules.put(	478	,"	Initiation aux réseaux en environnement CISCO (CCNA-1V5)	");
        modules.put(	497	,"	Immersion en entreprise	");
        modules.put(	506	,"	Utilisation des environnements Windows et GNU/Linux	");
        modules.put(	509	,"	Administration des systèmes informatiques	");
        modules.put(	513	,"	Virtualisation de serveurs : gestion et exploitation	");
        modules.put(	515	,"	Gestion de projet	");
        modules.put(	517	,"	Scripting et PowerShell	");
        modules.put(	519	,"	Téléphonie IP et Messagerie	");
        modules.put(	523	,"	Routage et commutation avancés en environnement CISCO (CCNA 2 et 3 V5)	");
        modules.put(	525	,"	Stage Systèmes et Réseaux	");
        modules.put(	527	,"	Utilisation et administration d'une base de données	");
        modules.put(	529	,"	Administration des systèmes informatiques : niveau avancé	");
        modules.put(	531	,"	Services réseaux et sécurité	");
        modules.put(	533	,"	Virtualisation de serveurs : conception et architecture	");
        modules.put(	536	,"	Serveurs WEB et serveurs d'applications	");
        modules.put(	541	,"	Développement Web côté client	");
        modules.put(	544	,"	Développement Web côté serveur avec PHP et Symfony	");
        modules.put(	552	,"	Analyse et conception	");
        modules.put(	553	,"	Projet analyse et conception	");
        modules.put(	560	,"	Développement Web côté serveur avec ASP.Net	");
        modules.put(	566	,"	Technologie multi plateforme (Cross-platform)	");
        modules.put(	576	,"	Immersion en entreprise	");
        modules.put(	625	,"	Projet systèmes et réseaux - 1	");
        modules.put(	648	,"	Algorithme et initiation à la programmation	");
        modules.put(	649	,"	SQL et initiation NoSQL	");
        modules.put(	650	,"	Programmation orientée objet avec Java	");
        modules.put(	655	,"	AngularJS	");
        modules.put(	692	,"	Algorithme et initiation programmation	");
        modules.put(	698	,"	Initiation à la programmation orientée objet et au langage SQL	");
        modules.put(	699	,"	Préparation à la certification CFTL/ISTQB niveau Fondation	");
        modules.put(	702	,"	Quality Center	");
        modules.put(	706	,"	TestLink : mise en oeuvre d'un gestionnaire de tests Open Source	");
        modules.put(	708	,"	Programmation orientée objet avec Java	");
        modules.put(	712	,"	Développement avec Java SE	");
        modules.put(	713	,"	Développement Web côté client (HTML-CSS-Javascript)	");
        modules.put(	714	,"	Développement Web avec Java EE	");
        modules.put(	715	,"	Cas pratique	");
        modules.put(	720	,"	TP système client	");
        modules.put(	721	,"	Services Réseaux	");
        modules.put(	722	,"	Services Réseaux (mise en  situation professionnelle)	");
        modules.put(	723	,"	Algorithme	");
        modules.put(	724	,"	Initiation à la programmation procédurale avec Java	");
        modules.put(	725	,"	Développement en couches avec Java	");
        modules.put(	727	,"	Projet 2 - Développement d'une application web 	");
        modules.put(	728	,"	Développement d'une application mobile avec Android	");
        modules.put(	729	,"	Projet 3 - Développement d'une application mobile	");
        modules.put(	730	,"	Projet 1 - Développement d'une application client-serveur	");
        modules.put(	731	,"	Administration d'un serveur Apache sous Linux	");
        modules.put(	733	,"	Bilan final	");
        modules.put(	734	,"	Analyse et conception	");
        modules.put(	735	,"	Base des réseaux	");
        modules.put(	736	,"	Assistance bureautique	");
        modules.put(	737	,"	Utilisation et administration d'un système Windows Client	");
        modules.put(	738	,"	Utilisation d'un système Linux	");
        modules.put(	739	,"	Services réseaux Windows	");
        modules.put(	740	,"	Virtualisation et clients légers	");
        modules.put(	741	,"	Messagerie	");
        modules.put(	742	,"	Gestion de parc	");
        modules.put(	743	,"	Modélisation et conception UML	");
        modules.put(	744	,"	Linux/Unix : utilisation en mode commande	");
        modules.put(	745	,"	Préparation à la certification ISTQB -  niveau Fondation	");
        modules.put(	746	,"	Développement avec Java	");
        modules.put(	747	,"	Immersion en entreprise	");
        modules.put(	749	,"	Théorie des réseaux locaux	");
        modules.put(	750	,"	Cas pratique	");
        modules.put(	751	,"	Hibernate/JPA	");
        modules.put(	752	,"	Selenium : automatiser les tests fonctionnels des applications Web	");
        modules.put(	754	,"	Développement web côté client	");
        modules.put(	755	,"	Développement Javascript	");
        modules.put(	756	,"	Le langage PHP	");
        modules.put(	757	,"	Développement avec le framework Symfony	");
        modules.put(	758	,"	Integration web	");
        modules.put(	759	,"	Mise en oeuvre d'un CMS	");
        modules.put(	760	,"	Projet : développement d'une application web	");
        modules.put(	761	,"	Conduite de projet	");
        modules.put(	762	,"	Analyse des besoins	");
        modules.put(	763	,"	Analyse et conception	");
        modules.put(	764	,"	CLOUD 	");
        modules.put(	765	,"	Organisation d'une DSI	");
        modules.put(	766	,"	CCNA Sécurité	");
        modules.put(	767	,"	Entretien mémoire & mise en commun projet	");
        modules.put(	768	,"	Qualité	");
        modules.put(	769	,"	Soutenance intermédiaire & mise en commun projet	");
        modules.put(	770	,"	Ethical Hacking	");
        modules.put(	771	,"	Sécurité du Système d'Information	");
        modules.put(	772	,"	ITIL	");
        modules.put(	773	,"	Business Intelligence	");
        modules.put(	774	,"	Présentation du projet d'études	");
        modules.put(	775	,"	Conduite de projet	");
        modules.put(	776	,"	Initiation aux réseaux	");
        modules.put(	777	,"	Analyse des besoins	");
        modules.put(	778	,"	Qualité	");
        modules.put(	779	,"	Soutenance intermédiaire & mise en commun projet	");
        modules.put(	780	,"	Urbanisation d'un système d'information	");
        modules.put(	782	,"	Agilité en gestion de projet 	");
        modules.put(	783	,"	Simulations d'entretiens	");
        modules.put(	784	,"	Entretien mémoire & mise en commun projet	");
        modules.put(	785	,"	ITIL & organisation d'une DSI	");
        modules.put(	786	,"	Administration d'une base de données SQL Server	");
        modules.put(	787	,"	Business Intelligence	");
        modules.put(	788	,"	Objets connectés - Marketing	");
        modules.put(	789	,"	Présentation du projet d'études	");
        modules.put(	790	,"	Présentation des métiers du numérique	");
        modules.put(	791	,"	Métiers \"Etudes et développement\"	");
        modules.put(	792	,"	Métiers \"Support et infrastructure\"	");
        modules.put(	793	,"	Autres métiers du numérique	");
        modules.put(	796	,"	UML	");
        modules.put(	797	,"	Rentrée	");
        modules.put(	798	,"	Programmation orientée objet avec Java	");
        modules.put(	799	,"	Projet 1 - Développement d'une application client-serveur	");
        modules.put(	800	,"	Mobilité européenne	");
        modules.put(	801	,"	Bilan - synthèse	");
        modules.put(	802	,"	Développement Web côté serveur avec PHP	");
        modules.put(	803	,"	Développement Web côté serveur avec Symfony	");
        modules.put(	804	,"	TP services réseaux hétérogènes	");
        modules.put(	805	,"	CMAP	");
        modules.put(	807	,"	Savoir être	");
        modules.put(	811	,"	Evaluations (2ème chance)	");
        modules.put(	812	,"	Bilan final	");
        modules.put(	813	,"	Rappels POO & SQL 	");
        modules.put(	814	,"	Développement d'une application objet avec C#	");
        modules.put(	815	,"	Développement web côté serveur avec ASP	");
        modules.put(	817	,"	Bilan final	");
        modules.put(	818	,"	Base des réseaux	");
        modules.put(	819	,"	Assistance bureautique	");
        modules.put(	820	,"	Utilisation et administration d’un système Windows client 	");
        modules.put(	821	,"	Utilisation et administration d’un système linux	");
        modules.put(	822	,"	Travaux pratiques - Système client	");
        modules.put(	823	,"	Services réseaux Windows	");
        modules.put(	824	,"	ITIL, gestion de parc et centre de support	");
        modules.put(	825	,"	Déploiement à partir de Microsoft Windows Server - Savoir-être & recherche d'emploi	");
        modules.put(	826	,"	Stage en entreprise	");
        modules.put(	827	,"	Services réseaux Linux	");
        modules.put(	828	,"	Travaux pratiques - Administration d’un réseau Windows serveur et Linux 	");
        modules.put(	829	,"	Serveur applicatif	");
        modules.put(	830	,"	Virtualisation et clients légers	");
        modules.put(	831	,"	Messagerie	");
        modules.put(	832	,"	Supervision	");
        modules.put(	833	,"	Projet final et evaluations	");
        modules.put(	834	,"	Session d'examen	");
        modules.put(	835	,"	SQL	");
        modules.put(	836	,"	Révision et passage de certification	");
        modules.put(	837	,"	Services Réseaux	");
        modules.put(	843	,"	Rappels sur la programmation en Java	");
        modules.put(	844	,"	CFTL Fondation	");
        modules.put(	845	,"	Semaine AGIR	");

        lieux.put(	1	,"	NANTES	");
        lieux.put(	2	,"	RENNES	");
        lieux.put(	3	,"	ST NAZAIRE	");
        lieux.put(	4	,"	ENI SERVICE	");
        lieux.put(	5	,"	NIORT	");
        lieux.put(	6	,"	AUTRE	");
        lieux.put(	7	,"	SALLE EDITIONS	");
        lieux.put(	8	,"	QUIMPER	");
        lieux.put(	9	,"	HUB CREATIC	");
        lieux.put(	10	,"	LA ROCHE SUR YON	");
        lieux.put(	11	,"	ANGERS	");
        lieux.put(	12	,"	LE MANS	");
        lieux.put(	13	,"	EUPTOYOU	");


        List<String> line = new ArrayList<>();
        PeriodeChoco periode = new PeriodeChoco(period);
        line.add("Du;Au");
        for (Calendar calendar:calendars)
        {
            line.set(0, line.get(0)+ ";Module;Lieu");


        }
        for (int i = periode.getDebut(); i <= periode.getFin(); i+=7)
        {
            java.util.Calendar debut = DateTimeHelper.toCalendar(i);
            debut.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.MONDAY);
            java.util.Calendar fin = DateTimeHelper.toCalendar(i);
            fin.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.FRIDAY);
            java.util.Calendar milieu = DateTimeHelper.toCalendar(i);
            milieu.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.WEDNESDAY);
            String OneLine = DateTimeHelper.toString(debut.toInstant(), "dd/MM/yyyy")+ ";" + DateTimeHelper.toString(fin.toInstant(), "dd/MM/yyyy");
            for (Calendar calendar:calendars)
            {
                String moduleLine = "";
                for (ClassesCalendar classe :calendar.getCours())
                {
                    if ((milieu.toInstant().isAfter(DateTimeHelper.toInstant(classe.getStart()))
                            && milieu.toInstant().isBefore(DateTimeHelper.toInstant(classe.getEnd())))
                            ||
                        (debut.toInstant().isBefore(DateTimeHelper.toInstant(classe.getStart()))
                         && fin.toInstant().isAfter(DateTimeHelper.toInstant(classe.getEnd()))))
                    {
                        moduleLine += ";" + modules.get(classe.getIdModule()) + ";" + lieux.get(classes.stream().filter(c -> c.getIdClasses().contentEquals(classe.getIdClasses())).mapToInt(m -> m.getIdPlace()).min().getAsInt());
                    }

                }
                if (moduleLine.contentEquals(""))
                {
                    moduleLine = ";;";
                }
                OneLine += moduleLine;
            }
            line.add(OneLine);
        }

        for (String l:line)
        {
            System.out.println(l);
        }
    }

    public static Problem getInput()
    {
        return initProblem();
    }

    private static void compare(List<Calendar> calendars)
    {
        for (Calendar calendar : calendars)
        {
            List<Calendar> autresCalendars = new ArrayList<>(calendars);
            autresCalendars.remove(calendar);
            List<String> stringStreamCalendar = calendar.getCours().stream().map(c2 -> c2.getIdClasses()).collect(Collectors.toList());
            autresCalendars.stream().filter(cal -> cal.getCours().stream().map(c -> c.getIdClasses()).allMatch(classe -> stringStreamCalendar.contains(classe))).forEach(c -> System.out.println("Doublon trouvé"));

        }
    }




    public static Problem initProblem()
    {
        final Problem problem = new Problem();
        List<Module>  modules = new ArrayList<Module>();


        // SELECT 'Module m' + REPLACE(m.LibelleCourt, '-', '') + ' = new Module (' +  CAST(m.IdModule AS VARCHAR(10) ) +', new HashSet<Module>(), new TreeSet<Classes>());' from module m left join ModuleParUnite mpu on m.IdModule = mpu.IdModule left join UniteParFormation upf on mpu.IdUnite = upf.Id where upf.CodeFormation = '17CDI   '

        Module m16DLPOOJ      = new Module(708, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Classes>(), 3, 105);
        Module mSQL           = new Module(20, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Classes>(), 1, 35);
        Module mPLSQL         = new Module(21, Arrays.asList(mSQL.getIdModule()), new ArrayList<Integer>(), new ArrayList<Classes>(), 2, 35);
        Module mJAVA1DL17     = new Module(725, Arrays.asList(m16DLPOOJ.getIdModule()), new ArrayList<Integer>(), new ArrayList<Classes>(), 1, 35);
        Module mPRJ1DEV17     = new Module(730, Arrays.asList(mJAVA1DL17.getIdModule()), new ArrayList<Integer>(), new ArrayList<Classes>(), 1, 35);
        Module mCONCDEV17     = new Module(734, Arrays.asList(m16DLPOOJ.getIdModule(), mJAVA1DL17.getIdModule()), new ArrayList<Integer>(), new ArrayList<Classes>(), 1, 35);
        Module mDVWEBCL       = new Module(541, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Classes>(), 1, 35);
        Module mDVWEBPH       = new Module(544, new ArrayList<Integer>(), Arrays.asList(mDVWEBCL.getIdModule()), new ArrayList<Classes>(), 1, 35);
        Module mJAVA2         = new Module(302, Arrays.asList(mJAVA1DL17.getIdModule()), Arrays.asList(mDVWEBCL.getIdModule()), new ArrayList<Classes>(), 1, 35);
        Module mPRJ2DEV17     = new Module(727, Arrays.asList(mJAVA2.getIdModule()), new ArrayList<Integer>(), new ArrayList<Classes>(), 1, 35);
        Module mJ2EAV         = new Module(34, Arrays.asList(mJAVA2.getIdModule()), new ArrayList<Integer>(), new ArrayList<Classes>(), 1, 35);
        Module mAPACCDI17     = new Module(731, new ArrayList<Integer>(), Arrays.asList(mDVWEBPH.getIdModule()), new ArrayList<Classes>(), 1, 35);
        Module mCPCOURS       = new Module(36, new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Classes>(), 1, 35);
        Module mDVWEBASPX     = new Module(560, new ArrayList<Integer>(), Arrays.asList(mDVWEBCL.getIdModule()), new ArrayList<Classes>(), 1, 35);
        Module mMOB1DEV17     = new Module(728, Arrays.asList(mJAVA1DL17.getIdModule()), Arrays.asList(mJAVA2.getIdModule()), new ArrayList<Classes>(), 1, 35);
        Module mPRJ3DEV17     = new Module(729, Arrays.asList(mMOB1DEV17.getIdModule()), new ArrayList<Integer>(), new ArrayList<Classes>(), 1, 35);
        Module mXAMARIN       = new Module(566, Arrays.asList(mDVWEBASPX.getIdModule()), Arrays.asList(mMOB1DEV17.getIdModule()), new ArrayList<Classes>(), 1, 35);
        Module m17BILAN_FINAL = new Module(817, Arrays.asList(mSQL.getIdModule(), mXAMARIN.getIdModule(), mMOB1DEV17.getIdModule(), mJ2EAV.getIdModule(), mPRJ3DEV17.getIdModule(), mDVWEBPH.getIdModule(), 232, 731 ) , new ArrayList<Integer>(), new ArrayList<Classes>(), 1, 35);


        // SELECT 'm' + REPLACE(m.LibelleCourt, '-', '') + '.getListClasses().add(new Classes(new Period("' + CONVERT(varchar, c.debut, 103) + '","' + CONVERT(varchar, c.fin, 103) + '", "dd/MM/yyyy HH:mm:ss"),"' + CAST(c.IdCours AS VARCHAR(40)) + '",' + CAST (m.IdModule AS VARCHAR(10))+ ','+ CAST (c.CodeLieu AS VARCHAR(10)) +'));' from cours c left join module m on c.IdModule = m.IdModule left join ModuleParUnite mpu on m.IdModule = mpu.IdModule left join UniteParFormation upf on mpu.IdUnite = upf.Id where upf.CodeFormation = '17CDI   '  order by m.IdModule
        mSQL.getListClasses().add(new Classes(new Period("2018-03-26", "2018-03-30"), "9AC9F5B9-BE0F-418D-AC3C-00EBB8582246", 11, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-03-26", "2018-03-30"), "EEF20121-7745-4C55-8259-06ED9888E0A4", 2, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-04-16", "2018-04-20"), "20D8C8D2-2FC7-4922-A4C6-1CAFF10C15D4", 1, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-04-16", "2018-04-20"), "7CCFF5C3-EEC0-486B-ADF9-1FAD5977D3B9", 1, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-04-16", "2018-04-20"), "49384010-C777-47F7-8BAF-25E8271AC403", 2, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-06-18", "2018-06-22"), "13FDBD8F-8F18-4A41-8B76-2F63CDF62FC5", 1, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-04-16", "2018-04-20"), "F02282BF-C22E-440B-BA13-31B6DABF19F5", 10, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-12-10", "2018-12-14"), "A7F50F72-8930-4C01-93F7-32388A4B91E2", 10, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-10-08", "2018-10-12"), "3F2BA010-10EA-412E-BF94-33E25B078A46", 1, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-01-22", "2018-01-26"), "71E6F84D-928F-41AE-B456-4321A8848462", 2, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-06-18", "2018-06-22"), "5D74CB18-FB70-4412-823C-56B26A3B123F", 11, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-12-03", "2018-12-07"), "B2165BF1-133F-4502-A621-6683E9929885", 1, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-02-05", "2018-02-09"), "A38899A4-DC5A-4B86-8FC6-8F7285513726", 12, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-10-08", "2018-10-12"), "BC2762CC-2544-4D01-8B47-B25DC6C07B12", 1, 35));
        mSQL.getListClasses().add(new Classes(new Period("2018-01-02", "2018-01-05"), "263AAA5E-994E-4D2B-AEFA-EED5E6B2639C", 1, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-04-03", "2018-04-06"), "46DB575D-76BB-40EC-BEBA-E408F4EE158B", 11, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-10-15", "2018-10-19"), "69D4F236-9A75-41C5-B103-ED63FABDDFE2", 1, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-04-23", "2018-04-27"), "B0BEE6FA-0DB7-4C3B-BAC1-B989FB149FD7", 10, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-04-23", "2018-04-27"), "188C569B-8C57-4EA2-9A1C-BD1A422C675E", 2, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-10-15", "2018-10-19"), "427EA863-2410-47C1-9825-B15DEB1BC565", 1, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-02-12", "2018-02-16"), "E286D7DC-EA10-4FBD-8A0A-C9C25919ADE0", 12, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-01-29", "2018-02-02"), "7A164585-9AC9-4B8F-9177-D19F14D8FECE", 2, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-12-17", "2018-12-21"), "C3AFAF49-73E8-4737-AA7A-8EEC81927D16", 10, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-12-10", "2018-12-14"), "F4F54845-1C84-4EB7-8F6A-5FD020B54B01", 1, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-04-03", "2018-04-06"), "C73EEC8A-481A-487A-A896-7E77C8A78F5D", 2, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-04-23", "2018-04-27"), "C5E55386-6F6F-4D49-BDB6-4661BCFD745F", 1, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-04-23", "2018-04-27"), "F15DB3EA-1AA8-4D13-8B96-48C3E58CE504", 1, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-06-25", "2018-06-29"), "602DC652-F338-4AB7-83C3-249FDFD49AD2", 1, 35));
        mPLSQL.getListClasses().add(new Classes(new Period("2018-06-25", "2018-06-29"), "7F4050E5-2B0A-4EAD-9847-080E8207296A", 11, 35));
        mJ2EAV.getListClasses().add(new Classes(new Period("2018-07-30", "2018-08-10"), "29C7B5D7-494E-45F2-A4B5-0BF69055A08E", 2, 70));
        mJ2EAV.getListClasses().add(new Classes(new Period("2019-04-08", "2019-04-19"), "079857A4-C961-4CA5-A3F9-0F7697F379D1", 2, 70));
        mJ2EAV.getListClasses().add(new Classes(new Period("2018-10-22", "2018-11-02"), "F1D989DA-24A7-4D62-A248-2A6615F93994", 11, 70));
        mJ2EAV.getListClasses().add(new Classes(new Period("2018-08-27", "2018-09-07"), "A140AE65-7FC8-4F93-AEE8-38A9D349EC19", 1, 70));
        mJ2EAV.getListClasses().add(new Classes(new Period("2019-02-18", "2019-03-01"), "3AB4CDEA-A36F-426F-87D1-AA7F45BAF6B2", 1, 70));
        mJ2EAV.getListClasses().add(new Classes(new Period("2019-04-15", "2019-04-26"), "217BF397-CB75-430C-AE2A-FF76AEF2196F", 1, 70));
        mCPCOURS.getListClasses().add(new Classes(new Period("2018-11-12", "2018-11-16"), "7487CDF9-CD97-44A6-8ECF-F9DF66748166", 11, 35));
        mCPCOURS.getListClasses().add(new Classes(new Period("2019-05-06", "2019-05-10"), "748346CF-194A-4F26-9C97-ED3790BF85C8", 1, 35));
        mCPCOURS.getListClasses().add(new Classes(new Period("2019-03-11", "2019-03-15"), "9D8B2B45-4750-4272-AF0F-718E62F037D4", 1, 35));
        mCPCOURS.getListClasses().add(new Classes(new Period("2018-09-17", "2018-09-21"), "993F8A4D-CBA5-412C-9966-3F3630F630E9", 1, 35));
        mCPCOURS.getListClasses().add(new Classes(new Period("2018-08-20", "2018-08-24"), "3801F745-4FED-4E05-B9A6-4998DBC7C480", 2, 35));
        mCPCOURS.getListClasses().add(new Classes(new Period("2019-05-27", "2019-05-31"), "01CF703D-66AD-413B-8940-46BF4684F885", 2, 35));
        mJAVA2.getListClasses().add(new Classes(new Period("2019-03-11", "2019-03-22"), "1F2053DA-D71F-4D3A-8183-466B19E34B39", 1, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2018-07-16", "2018-07-27"), "A6F82AF0-CAB6-42FB-A1B2-56B7A7ED1CEF", 10, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2019-01-14", "2019-01-25"), "601CC889-8343-4E62-89AE-540022CDF3F4", 1, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2018-09-17", "2018-09-28"), "CB4DD72B-0C0E-45E7-932E-3A1D75F9841C", 1, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2018-01-29", "2018-02-09"), "86BD5A87-8796-4841-96C2-30999B3ABE7A", 1, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2018-06-25", "2018-07-06"), "C90FF4CC-8847-4895-A0F4-074717D248D1", 11, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2019-03-18", "2019-03-29"), "00CB9CE4-0DEA-47A3-95B4-717EB14BA7EB", 10, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2018-07-16", "2018-07-27"), "854AB776-5894-4CEB-BE4B-A6702A2F1398", 1, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2019-01-07", "2019-01-18"), "C2B13BEE-AF8C-4B01-9138-96472CED391C", 2, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2018-04-30", "2018-05-04"), "22D0DC71-08F4-4FFD-BC5A-8E5F9DD1F65C", 12, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2018-07-16", "2018-07-27"), "9C159163-395C-4C42-8A55-8205C8A0AADD", 1, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2018-05-14", "2018-05-18"), "AE328890-2F0D-4159-9E8F-83614E398E29", 12, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2019-01-14", "2019-01-25"), "91A46A5E-F1B3-41BF-8B89-E56D82E1952D", 1, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2018-06-25", "2018-07-06"), "2719B3A2-AB19-49C5-8BF2-FDB79C75A0E6", 2, 70));
        mJAVA2.getListClasses().add(new Classes(new Period("2018-09-17", "2018-09-28"), "B9ACABF3-51FA-460B-9016-FE647A831F2D", 11, 70));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-12-03", "2018-12-07"), "7F6F1BEA-4AD3-409F-B7D4-1D8233BB72F9", 1, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-06-18", "2018-06-22"), "1EFC4886-2EE8-4526-84CF-1DC2FCD3246F", 10, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-08-20", "2018-08-24"), "B050CD0E-2390-45C0-918B-1EACB3EFF211", 1, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-08-20", "2018-08-24"), "CDF20F3C-94D4-4069-A640-00E3100FDAE1", 11, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-05-28", "2018-06-01"), "DF59BE30-CBE4-4694-8470-5715606FE9C9", 11, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-05-28", "2018-06-01"), "219CC390-7A58-477F-83D8-75278977BC67", 2, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-04-03", "2018-04-06"), "3C6B1394-78A3-4EE8-A23D-85BABF0D24E2", 12, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-06-18", "2018-06-22"), "B41E4437-5145-498A-A1C4-8DF1BE253365", 1, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2019-02-18", "2019-02-22"), "9CD9F784-76BC-4BA3-AA82-CABB25ACA379", 10, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-06-18", "2018-06-22"), "3616294E-D6BA-484F-AE40-B8DEF50FFA77", 1, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2019-02-11", "2019-02-15"), "EC7AECCD-A8EE-436C-B9B5-D20A816744B7", 1, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-10-08", "2018-10-12"), "B061528A-4125-400C-A348-BDDA7469D42F", 2, 35));
        mDVWEBCL.getListClasses().add(new Classes(new Period("2018-12-03", "2018-12-07"), "D7D0B8AD-841E-4E77-9951-EFBD2EE80481", 1, 35));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-06-25", "2018-07-13"), "EDBF0441-50CC-4B38-A0C0-E0A79C95E2E9", 10, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-08-27", "2018-09-14"), "2C2B0501-487C-46F7-A98C-E1549BD944F5", 11, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2019-02-25", "2019-03-15"), "C1F8614E-DA58-4620-A20E-E6B6461D746B", 10, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2019-01-07", "2019-01-11"), "3145CAFA-0E4B-420A-8265-B8143624B40B", 1, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-12-10", "2018-12-21"), "966E9CF6-1126-474A-A8CC-D01EA99E3735", 1, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2019-02-18", "2019-03-08"), "E3BE7F4B-A4B3-4675-80BA-87381D26EBC1", 1, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-06-04", "2018-06-22"), "E9AA5E81-77B0-43DB-B2F2-A77F62D16933", 11, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-06-25", "2018-07-13"), "AD33FBE0-C07F-4737-9C2B-7ED85F26805C", 1, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2019-01-07", "2019-01-11"), "1B147CB2-F7DC-45E4-BDD6-6BDF029A362D", 1, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-12-10", "2018-12-21"), "A8FD9341-5439-4C7E-AFF4-6966F14F5BC0", 1, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-06-04", "2018-06-22"), "EB2ACA76-6421-4514-A1B8-5C1781B98609", 2, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-11-19", "2018-11-30"), "0B839480-E37D-48D8-BABB-55B61ED78431", 2, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-08-27", "2018-09-14"), "934042C9-1EF3-4B64-9F25-4D153C54042F", 1, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-06-25", "2018-07-13"), "E9093CE0-CFFA-4962-8276-442B4F3FA86C", 1, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-10-15", "2018-10-19"), "AB2AE0F6-793C-4DB9-9701-297506E39300", 2, 105));
        mDVWEBPH.getListClasses().add(new Classes(new Period("2018-04-09", "2018-04-27"), "85479554-E63F-44D0-81A9-0BD66A5F262D", 12, 105));
        mDVWEBASPX.getListClasses().add(new Classes(new Period("2019-07-01", "2019-07-19"), "4EBD9C3F-373D-468D-BE7C-0C54BB88410C", 2, 105));
        mDVWEBASPX.getListClasses().add(new Classes(new Period("2019-03-18", "2019-04-05"), "AD6EFD1D-80DA-4063-8D3B-411954AB3F32", 1, 105));
        mDVWEBASPX.getListClasses().add(new Classes(new Period("2018-11-19", "2018-12-07"), "59B0F1DC-DAEB-4BF4-BB81-484F3919F66B", 11, 105));
        mDVWEBASPX.getListClasses().add(new Classes(new Period("2018-09-24", "2018-10-12"), "75EB593F-DF78-4A14-848B-5313A4BE520B", 1, 105));
        mDVWEBASPX.getListClasses().add(new Classes(new Period("2018-08-27", "2018-09-14"), "CFC787BF-FE59-4FE7-8E4F-7C2AA5C47CAE", 2, 105));
        mDVWEBASPX.getListClasses().add(new Classes(new Period("2019-05-13", "2019-05-31"), "B4B5E257-3F55-4555-87EE-8F32F5B5DC3A", 1, 105));
        mXAMARIN.getListClasses().add(new Classes(new Period("2018-11-05", "2018-11-09"), "D9E80C62-D60F-4D41-A938-80C44A7FFAE4", 1, 35));
        mXAMARIN.getListClasses().add(new Classes(new Period("2019-01-14", "2019-01-18"), "EA4755F1-3C0E-43D7-9326-A1C999F30E7D", 11, 35));
        mXAMARIN.getListClasses().add(new Classes(new Period("2019-06-24", "2019-06-28"), "174DEC07-CCB1-482F-8CE3-910DDA1F7467", 1, 35));
        mXAMARIN.getListClasses().add(new Classes(new Period("2019-09-16", "2019-09-20"), "EBD828B0-4CF0-464D-876C-929B8A5C8E80", 2, 35));
        mXAMARIN.getListClasses().add(new Classes(new Period("2019-10-21", "2019-10-25"), "2AD6370A-5C01-49E7-9342-5EC4DF37783D", 2, 35));
        mXAMARIN.getListClasses().add(new Classes(new Period("2018-10-08", "2018-10-12"), "CA3B48C2-2729-489D-9BC5-33225F26337B", 2, 35));
        mXAMARIN.getListClasses().add(new Classes(new Period("2019-04-29", "2019-05-03"), "D3E9EE90-4BDB-432B-823E-DEA0FA2B1B9D", 1, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-11-26", "2018-11-30"), "D090B325-1AB8-48D6-99C5-E0585D14D05A", 1, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-03-19", "2018-03-23"), "8DCF25E3-1B22-47AF-9400-C891A50240A3", 2, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-06-11", "2018-06-15"), "8FF17DC2-3AE1-4E39-BCDE-B115D6536F61", 11, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-03-19", "2018-03-23"), "91163186-74AE-4458-8B12-E5106F8DC105", 11, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-12-03", "2018-12-07"), "23C7F6A7-D917-4C3A-84A7-1FFEA8F83016", 10, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-04-09", "2018-04-13"), "9D39D0F3-395F-4C02-8DCF-222934035F4A", 1, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-01-29", "2018-02-02"), "2BCA7AFD-9A08-479C-B1BA-0B09CD4DF4A6", 12, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-10-01", "2018-10-05"), "9004BC97-0BBA-4CDA-BB17-439B7C6B3025", 1, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-04-09", "2018-04-13"), "88EED523-8B7C-4F5C-B71A-3D29C589FDB8", 10, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2017-12-11", "2017-12-15"), "77D66D8A-DEFD-44F6-A158-65DD5A34400E", 2, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-04-09", "2018-04-13"), "1EB21C6B-7E5F-4921-A3AB-6D64053DA378", 1, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-06-11", "2018-06-15"), "C24C868C-1BD2-4FC8-89D4-6E18F47F23C0", 1, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2017-12-18", "2017-12-22"), "8197E1DD-1169-4E74-82CF-A0576081D58D", 1, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-04-09", "2018-04-13"), "27C4662D-83FD-4A38-B3B3-AA8CB7F292ED", 2, 35));
        m16DLPOOJ.getListClasses().add(new Classes(new Period("2018-10-01", "2018-10-05"), "3AA758F1-2C0A-4570-AA3F-85F84117E429", 1, 35));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-05-14", "2018-05-25"), "79125B83-EF81-4387-AF74-7FDFDF86E8A8", 2, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-04-09", "2018-04-20"), "85239D35-A51E-4430-8B4F-8B05E5159614", 2, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-12-17", "2018-12-21"), "A213824B-6763-4780-AC44-72A3B327D137", 1, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-05-14", "2018-05-18"), "7C6D3F41-9EA3-41B9-98F5-5A2D5955E2CE", 1, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-05-14", "2018-05-18"), "82B76FAB-C89C-41A7-AC7F-3ECFD6E71643", 1, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-07-02", "2018-07-13"), "F818424D-09CA-4E5A-A405-42DDEEBF1B64", 1, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-10-22", "2018-11-02"), "BD621130-84B8-4BA6-9EBA-4D75AE05684B", 1, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-04-30", "2018-05-04"), "77E51E57-4DC7-4C03-95F2-52D8FFDC37B2", 10, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2019-01-07", "2019-01-18"), "7F8F6DB0-54A7-4E74-A2FC-58A96E11F60E", 10, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-02-19", "2018-03-02"), "1D13DBC1-8AF0-4293-9BE0-19F53994F350", 12, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-07-02", "2018-07-13"), "C2D24013-85C0-4661-A498-23D2F8CAA962", 11, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-04-09", "2018-04-20"), "B887EFE6-DCD8-4473-8615-E660E5A64FC0", 11, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-04-30", "2018-05-04"), "9AF796A9-ABF2-45EC-8068-F616B71A01D6", 1, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-04-30", "2018-05-04"), "87C761B2-D842-4182-B044-FBD5E7AB0647", 1, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2019-01-07", "2019-01-11"), "FA99B569-3A8E-48FC-84E5-AE08661E90A4", 1, 70));
        mJAVA1DL17.getListClasses().add(new Classes(new Period("2018-10-22", "2018-11-02"), "FF2F4269-F3FF-40B4-928F-B266146EF73F", 1, 70));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2019-03-25", "2019-04-12"), "03DFABF1-9405-499B-8F73-AC917ED5532D", 1, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2019-01-28", "2019-02-15"), "33A95D4A-3C4F-46AC-B107-ADF5BABD63DA", 1, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2019-02-18", "2019-03-08"), "16E23474-A904-4CEB-B0B4-C5EB9963698F", 2, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2018-10-01", "2018-10-19"), "F10880DF-9F18-41B3-85E1-C77C5031AD4F", 11, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2018-05-22", "2018-06-08"), "E24C6E81-0971-4C93-A749-EB516155B848", 12, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2018-08-20", "2018-08-24"), "BEC4E65E-3A92-431A-AA83-27C3352B53F3", 1, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2018-08-20", "2018-08-24"), "6A4C51CD-529F-4B6A-B8C2-0E1A7A7FC51C", 1, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2018-07-30", "2018-08-10"), "FC681319-FD7B-4250-AD48-09075D0CC074", 10, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2019-04-01", "2019-04-19"), "3E17AF8A-FC24-4BDA-8010-0203826B9899", 10, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2018-10-01", "2018-10-19"), "6A739FAD-0733-45AE-AB87-5623600452B0", 1, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2018-07-09", "2018-07-27"), "C649E589-F653-4704-BC22-446E44625734", 11, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2018-07-30", "2018-08-10"), "6075D1E3-FD17-453D-8B86-5B9B4CBFA5AC", 1, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2018-07-09", "2018-07-27"), "40C0A526-F3AB-4FEC-B730-8C3AF52CED7E", 2, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2018-07-30", "2018-08-10"), "4ECFA3E8-2D13-4BF2-A49A-8167936CF76E", 1, 105));
        mPRJ2DEV17.getListClasses().add(new Classes(new Period("2019-01-28", "2019-02-15"), "6C77C766-656F-4DAE-93E8-84F3E7E45BDB", 1, 105));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2019-06-03", "2019-06-14"), "115E3C13-AA90-4D23-A8C9-9A8FCC942F4D", 1, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2018-07-30", "2018-08-10"), "D45D5334-127F-450D-A4D5-9E35D80FEC6F", 11, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2019-02-18", "2019-03-01"), "20E21241-4344-483B-BBA2-6B776FF9A5C7", 1, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2018-08-27", "2018-09-07"), "2F8FC23C-7DF0-48ED-B0B9-3C542E439217", 1, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2019-04-23", "2019-05-03"), "445A8790-B375-403F-8134-5646089D5A19", 10, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2019-04-08", "2019-04-19"), "4F947DFC-520B-421C-98E8-064181CE9270", 1, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2018-10-15", "2018-10-26"), "CDAFADCB-7FF8-4B8C-8DBE-1C3A57069C63", 1, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2018-08-27", "2018-09-07"), "98B4405A-1760-4C4D-A131-1F23D754E51F", 10, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2018-12-10", "2018-12-21"), "D8DC6F44-3BA8-4722-81F9-2F2B89BBD8FC", 11, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2018-06-11", "2018-06-22"), "446C385E-707F-4113-996D-DCF7DC16D216", 12, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2019-08-26", "2019-09-06"), "9E10FA5B-8D32-4844-A86F-E4BD5133D1BC", 2, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2018-09-17", "2018-09-28"), "0950D373-0902-4784-94BC-BEC90A8BD01A", 2, 70));
        mMOB1DEV17.getListClasses().add(new Classes(new Period("2018-10-22", "2018-11-02"), "F522EAF4-5A9B-4927-9919-B383A3116AB0", 1, 70));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2019-03-04", "2019-03-08"), "5DE27295-322F-4A96-8DAC-C3DC0BAA19F9", 1, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2018-10-29", "2018-11-02"), "C357AB39-F257-4720-A965-DB9FB923AEA8", 1, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2019-05-06", "2019-05-10"), "58E280BA-5356-4816-874F-E4FF4F5C8C22", 10, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2019-06-17", "2019-06-21"), "F53B456F-CBE6-424C-9BAF-594CAB2190AE", 1, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2018-10-01", "2018-10-05"), "DEB4033D-5E7A-4785-9C74-4BB837C555A0", 2, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2018-06-25", "2018-06-29"), "9A0EC673-1147-4DC7-BE4C-38D70CBC4D26", 12, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2018-11-05", "2018-11-09"), "A7EEED71-3F90-47B5-AB71-38F1442E834D", 1, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2019-04-23", "2019-04-26"), "F20D680F-2940-48D8-92AA-695CD09E40B1", 1, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2018-09-10", "2018-09-14"), "CAB51343-77CE-4A05-9A27-75BDA17AFAAD", 10, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2019-09-09", "2019-09-13"), "1A53B31D-5197-4855-99BE-744677A5D04B", 2, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2019-01-07", "2019-01-11"), "1931AEFE-92B7-4E2E-AFD2-AAA03DC369A7", 11, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2018-09-10", "2018-09-14"), "A6ECA816-248F-4361-BEC0-9D9622BE6C1D", 1, 35));
        mPRJ3DEV17.getListClasses().add(new Classes(new Period("2018-08-13", "2018-08-17"), "1DB7E74B-FEB9-43B7-88F1-A1D7C149E512", 11, 35));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-04-23", "2018-05-04"), "3025FE4D-6C48-429F-B140-9CF881B8DF11", 2, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2019-01-21", "2019-02-01"), "058B4374-63DC-4416-A2E9-948160022AC8", 10, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-03-05", "2018-03-16"), "460B6F26-60E3-46B1-B5DA-8222CA7E7508", 12, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-06-25", "2018-07-06"), "F679E790-176B-4E64-9EE4-7DDA8F7CDE57", 2, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2019-01-14", "2019-01-25"), "7D4FA370-30F2-4F59-9E3C-6D53C01DAA7C", 1, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-07-16", "2018-07-27"), "9D2AF640-BC7A-48D8-BB73-3890A44972E5", 11, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-05-22", "2018-06-01"), "AB448A09-D837-481D-A51E-FDCC1ADEEEDE", 1, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-05-22", "2018-06-01"), "8B4BF1A0-9E34-4ACC-AB03-FFEC69085EDA", 10, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-11-05", "2018-11-16"), "7D237DD2-4F1C-41DB-9B09-D233A5191A18", 1, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-11-05", "2018-11-16"), "5321A049-620F-41C5-B0B8-D85F07716661", 1, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-05-22", "2018-06-01"), "F67D20B5-7972-433B-AE34-CB9CB30085CF", 1, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-07-16", "2018-07-27"), "E2528FDB-2DCB-49F5-BF42-CE4C3BD0E6DB", 1, 70));
        mPRJ1DEV17.getListClasses().add(new Classes(new Period("2018-04-23", "2018-05-04"), "FAFE4C71-4220-48EB-BEF9-C836483B77B6", 11, 70));
        mAPACCDI17.getListClasses().add(new Classes(new Period("2018-09-10", "2018-09-14"), "C9EAD594-44CE-4F79-88E2-D1B75D274942", 1, 35));
        mAPACCDI17.getListClasses().add(new Classes(new Period("2019-03-04", "2019-03-08"), "FABB62A4-66D5-458C-846C-E6C8927249DC", 1, 35));
        mAPACCDI17.getListClasses().add(new Classes(new Period("2019-05-20", "2019-05-24"), "B68EA7BA-5871-4093-95C9-79B5CE0C6C71", 2, 35));
        mAPACCDI17.getListClasses().add(new Classes(new Period("2018-11-05", "2018-11-09"), "D7D4E588-3FC1-4F43-918E-823773D7BF57", 11, 35));
        mAPACCDI17.getListClasses().add(new Classes(new Period("2019-04-29", "2019-05-03"), "1C46C421-3EF5-4938-8C81-9E4F556A00D5", 1, 35));
        mAPACCDI17.getListClasses().add(new Classes(new Period("2018-08-13", "2018-08-17"), "F6F85C5D-AD6F-47C1-BB5F-9FDC333DD6C4", 2, 35));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-03-05", "2018-03-16"), "B15F237E-0FA4-43F0-A647-85DB803BF6C5", 1, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-07-30", "2018-08-10"), "85A5E18B-58E8-4C25-B95D-7E832D88D557", 1, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-11-19", "2018-11-30"), "45AA1DB4-9BE1-4584-9D43-7E8F522D13AC", 1, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2019-02-04", "2019-02-15"), "BAFD1012-9E8C-4D8D-9775-74056AB7E3E9", 10, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-05-14", "2018-05-25"), "49C58519-D02B-4473-B0A5-3FA251FF6490", 11, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-08-20", "2018-08-31"), "F9509972-B829-47FC-80AD-1FDF4DB35936", 2, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-06-04", "2018-06-15"), "646789C9-51F0-4641-B6A3-26527F863A38", 1, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-05-14", "2018-05-25"), "54263645-E9D2-4825-879C-E81C1C3E4A81", 2, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-11-19", "2018-11-30"), "A6FE3680-0EDF-439B-B8F3-EABAE3F1F3B0", 1, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-07-30", "2018-08-10"), "84531CF4-AA01-4C59-8D34-E53A3B39F8DF", 11, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-03-19", "2018-03-30"), "DE0F7590-2F6D-42FE-9ED1-FCB2C6DB2B65", 12, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-06-04", "2018-06-15"), "9A34D0AF-C62D-4BAA-BBD6-CEAF67FFC7AE", 1, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2018-06-04", "2018-06-15"), "D820AA47-D8B8-47AA-91DE-D864EB65C4EA", 10, 70));
        mCONCDEV17.getListClasses().add(new Classes(new Period("2019-01-28", "2019-02-08"), "B58AAB97-439E-4D58-9FE5-B392733F5E6B", 1, 70));
        m17BILAN_FINAL.getListClasses().add(new Classes(new Period("2018-11-19", "2018-11-20"), "2CF00DDF-FB7B-4C16-8C36-6C23B776FD86", 2, 14));
        m17BILAN_FINAL.getListClasses().add(new Classes(new Period("2019-11-19", "2019-11-20"), "2DDD0DDF-FB7B-4C16-8C36-6C23B776FD86", 2, 14));

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


        problem.setModuleOfTraining(modules);
        problem.setPeriodOfTraining(new Period("2017-01-02", "2019-11-24"));
        problem.setConstraints(new Constraint());
        modules.stream().forEach(m -> m.setListClasses(m.getListClasses().stream().filter(d ->
                                                                                                  DateTimeHelper.toInstant(d.getPeriod().getStart()).isAfter(DateTimeHelper.toInstant(problem.getPeriodOfTraining().getStart())) &&
                                                                                                  DateTimeHelper.toInstant(d.getPeriod().getEnd()).isBefore(DateTimeHelper.toInstant(problem.getPeriodOfTraining().getEnd()))).collect(Collectors.toList())));

        /*
        new IntegerConstrainte(1, 5),
                new IntegerConstrainte(1500, 4),
                new IntegerConstrainte(3000, 3),
                new IntegerConstrainte(20, 2),
                Arrays.asList(new Period("2018-03-19", "2018-03-23")),
                Arrays.asList(new Period("2019-04-01","2019-04-19")),
                new IntegerConstrainte(3, 1),
                Arrays.asList(),
                Arrays.asList()
                        )

        );

        problem.getContraintes().setTrainingFrequency(new ConstraintPriority<TrainingFrequency>(1, new TrainingFrequency(1,2)));
        problem.getContraintes().setMaxWeekInTraining(new IntegerConstrainte(3, 1));
*/

        problem.getConstraints().setPlace(new ConstraintPriority<Integer>(7, 2));
        ConstraintPriority<TrainingFrequency> frequence = new ConstraintPriority<>(8, new TrainingFrequency());
        frequence.getValue().setMinWeekInCompany(3);
        frequence.getValue().setMaxWeekInTraining(3);
        problem.getConstraints().setTrainingFrequency(frequence);
        problem.getConstraints().setListPeriodeOfTrainingExclusion(Arrays.asList(new ConstraintPriority<>(5, new Period("2018-05-21", "2018-05-27"))));
        problem.getConstraints().setListPeriodeOfTrainingInclusion(Arrays.asList(new ConstraintPriority<>(4, new Period("2018-04-02", "2018-04-06"))));


        return problem;
    }

}
