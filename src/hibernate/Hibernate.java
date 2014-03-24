package hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import elementit.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;

public class Hibernate {

    public Hibernate() {
    }

    public void parse() {

        SessionFactory tehdas = new AnnotationConfiguration().configure().buildSessionFactory();
        Session istunto = tehdas.openSession();

        Transaction transaktio = null;

        try {
            transaktio = istunto.beginTransaction();

            Kategoria k1 = new Kategoria(0, "Hedelmä");
            Kategoria k2 = new Kategoria(0, "Viina");
            Kategoria k3 = new Kategoria(0, "Mixeri");
            Ainesosa a1 = new Ainesosa(0, "Lime", k1);
            Ainesosa a2 = new Ainesosa(0, "Rommi (Vaalea)", k2);
            Ainesosa a3 = new Ainesosa(0, "Colalimsa", k3);
            Drinkki d1 = new Drinkki(0, "Cuba Libre");
            istunto.saveOrUpdate(a1);
            istunto.save(a2);
            istunto.save(a3);
            istunto.save(k1);
            istunto.save(k2);
            istunto.save(k3);

            DrinkinAinesosat da1 = new DrinkinAinesosat();
            da1.setDrinkki(d1);
            da1.setAinesosa(a1);
            da1.setMaara("0.25 kpl");

            DrinkinAinesosat da2 = new DrinkinAinesosat();
            da2.setDrinkki(d1);
            da2.setAinesosa(a2);
            da2.setMaara("4 cl");

            DrinkinAinesosat da3 = new DrinkinAinesosat();
            da3.setDrinkki(d1);
            da3.setAinesosa(a3);
            da3.setMaara("10 cl");

            d1.getAinesosat().add(da1);
            d1.getAinesosat().add(da2);
            d1.getAinesosat().add(da3);
            istunto.saveOrUpdate(d1);

            transaktio.commit();
        } catch (Exception e) {
            if (transaktio != null && transaktio.isActive()) {
                try {
                    transaktio.rollback();
                } catch (HibernateException e1) {
                    System.err.println("Tapahtuman peruutus epäonnistui.");
                }
            }
            e.printStackTrace();
        } finally {
            istunto.close();
        }
    }

    public void addDrinkki(Drinkki drinkki) {
        SessionFactory tehdas = new AnnotationConfiguration().configure().buildSessionFactory();
        Session istunto = tehdas.openSession();
        
        Transaction transaktio = null;

        try {
            transaktio = istunto.beginTransaction();

            String hql;
            hql = "FROM Drinkki WHERE nimi = :nimi";
            Query drinkkiKysely = istunto.createQuery(hql);
            drinkkiKysely.setParameter("nimi", drinkki.getNimi());
            List<Drinkki> drinkit = drinkkiKysely.list();
            if (drinkit.size() > 0) {
            } else {
                hql = "FROM Kategoria WHERE nimi = :nimi";
                Query kategoriaKysely = istunto.createQuery(hql);
                hql = "FROM Ainesosa WHERE nimi = :nimi";
                Query ainesosaKysely = istunto.createQuery(hql);

                Set<DrinkinAinesosat> daSet = drinkki.getAinesosat();
                for (DrinkinAinesosat da : daSet) {

                    ainesosaKysely.setParameter("nimi", da.getAinesosa().getNimi());
                    List<Ainesosa> ainesosat = ainesosaKysely.list();

                    if (ainesosat.size() > 0) {
                        da.setAinesosa(ainesosat.get(0));
                    } else {
                        kategoriaKysely.setParameter("nimi", da.getAinesosa().getKategoria().getNimi());
                        List<Kategoria> kategoriat = kategoriaKysely.list();
                        if (kategoriat.size() > 0) {
                            da.getAinesosa().setKategoria(kategoriat.get(0));
                        } else {
                            istunto.saveOrUpdate(da.getAinesosa().getKategoria());
                        }

                        istunto.saveOrUpdate(da.getAinesosa());
                    }

                }
                istunto.saveOrUpdate(drinkki);
            }

            transaktio.commit();
        } catch (Exception e) {
            if (transaktio != null && transaktio.isActive()) {
                try {
                    transaktio.rollback();
                } catch (HibernateException e1) {
                    System.err.println("Tapahtuman peruutus epäonnistui.");
                }
            }
            e.printStackTrace();
        } finally {
            istunto.close();
        }
    }

    public List<Drinkki> loadDatabase() {
        SessionFactory tehdas = new AnnotationConfiguration().configure().buildSessionFactory();
        Session istunto = tehdas.openSession();

        Transaction transaktio = null;
        List<Drinkki> drinkkilista = null;
        try {
            transaktio = istunto.beginTransaction();
            String hql = "FROM Drinkki";
            drinkkilista = istunto.createQuery(hql).list();
            transaktio.commit();
        } catch (Exception e) {
            if (transaktio != null && transaktio.isActive()) {
                try {
                    transaktio.rollback();
                } catch (HibernateException e1) {
                    System.err.println("Tapahtuman peruutus epäonnistui.");
                }
            }
            e.printStackTrace();
        } finally {
            istunto.close();
        }
        return drinkkilista;
    }

    public void poistaDrinkki(Drinkki drinkki) {
        SessionFactory tehdas = new AnnotationConfiguration().configure().buildSessionFactory();
        Session istunto = tehdas.openSession();

        Transaction transaktio = null;
        try {
            transaktio = istunto.beginTransaction();
            istunto.delete(drinkki);
            transaktio.commit();
        } catch (Exception e) {
            if (transaktio != null && transaktio.isActive()) {
                try {
                    transaktio.rollback();
                } catch (HibernateException e1) {
                    System.err.println("Tapahtuman peruutus epäonnistui.");
                }
            }
            e.printStackTrace();
        } finally {
            istunto.close();
        }
    }
}
