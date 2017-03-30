package ex3.serveur;

import ex3.serveur.LivreEmp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2017-03-30T18:00:44")
@StaticMetamodel(Emprunteur.class)
public class Emprunteur_ { 

    public static volatile SingularAttribute<Emprunteur, Integer> nblivresemp;
    public static volatile SingularAttribute<Emprunteur, Integer> numemp;
    public static volatile SingularAttribute<Emprunteur, String> nom;
    public static volatile ListAttribute<Emprunteur, LivreEmp> livres;

}