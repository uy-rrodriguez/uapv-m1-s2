package client;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import serveur.MonBean;
import serveur.MonBean2;


public class ClientEx1 {
	public static void main(String[] args) {
		try {
			InitialContext ic = new InitialContext();
			
			System.out.println("Test bean 1 : stateless");
			MonBean bean1 = (MonBean) ic.lookup("serveur.MonBean");
			String user = "userStateless";
			System.out.println(bean1.salut(user));
			
			System.out.println("Test bean 2 : stateful");
			MonBean2 bean2 = (MonBean2) ic.lookup("serveur.MonBean2");
			user = "userStateful";
			System.out.println(bean2.salut(user));
			System.out.println(bean2.re_salut());
		}
		catch (NamingException e) {
			e.printStackTrace();
		}
	}
}
