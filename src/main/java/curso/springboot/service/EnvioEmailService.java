package curso.springboot.service;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import curso.springboot.model.UsuarioModel;


@Service
public class EnvioEmailService {

	private String corpoEmail = "";
	private static final String USERNAME = "gustavodevjava@gmail.com";
	private static final String PASSWORD= "Krypt@on15*";
	
	@Autowired(required = true)
	private ConfigMail properties;
	
	
	public void envioEmailUsuario(UsuarioModel usuario) {
		
		try {
			
			String emailUsuario = usuario.getEmail();
			String loginUsuario = usuario.getLogin();
			String senhaUsuario = usuario.getSenha();
			
			Session session = Session.getInstance(properties.propriedadesMail(), new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			});
			
			
			Address[] toUser = InternetAddress.parse(emailUsuario);
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(USERNAME, "Spring Boot Project"));
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Conta criada!");
			

			StringBuilder stringBuilderCorpoEmail = new StringBuilder();
			
			stringBuilderCorpoEmail.append("<h2>Olá, <p>");
			stringBuilderCorpoEmail.append("<h2>A criação de sua conta foi efetuada com sucesso. Agora você já pode acessar o sistema com o login e senha informados na criação desta.</h2><p>");
			stringBuilderCorpoEmail.append("<h2>Login: " + loginUsuario + "</h2>");
			stringBuilderCorpoEmail.append("<h2>Senha: " + senhaUsuario + "</h2><p>");
			stringBuilderCorpoEmail.append("<a style=\"color:#000000; padding: 9px 10px; text-align:center; text-decoration: none; font-size: 15px; display:inline-block; border-radius: 13px; border: 1px solid black; background-color:#D3D3D3\" target=\"_blank\" href=\"http://portifoliojavawebgustavo.com/springboot/logout\">Acessar Sistema</a><br/><br/>");
			stringBuilderCorpoEmail.append("<br/><h2 style=\"font-family:Trebuchet MS;\">Att, </h2><p>");
			stringBuilderCorpoEmail.append("<h2 style=\"font-family:Trebuchet MS;\">Gustavo da Silva Paulo</h2>");
			stringBuilderCorpoEmail.append("<h2 style=\"font-family:Trebuchet MS;\">Contato: (13) 98228-8624</h2>");
			stringBuilderCorpoEmail.append("<h2 style=\"font-family:Trebuchet MS;\">Portfólio: <a target=\"_blank\" href=\"https://portfoliojava.herokuapp.com\">portfoliojava.herokuapp.com</a></h2>");
			
			corpoEmail = stringBuilderCorpoEmail.toString();
			message.setContent(corpoEmail, "text/html; charset=utf-8");
	
			
			Transport.send(message);
            
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		public void enviaRelatorioEmail(String enviarPara, byte[] pdf) {

			try {

				String emailUsuario = enviarPara;

				Session session = Session.getInstance(properties.propriedadesMail(), new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(USERNAME, PASSWORD);
					}
				});

				Address[] toUser = InternetAddress.parse(emailUsuario + USERNAME);

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(USERNAME, "Spring Boot Project"));
				message.setRecipients(Message.RecipientType.TO, toUser);
				message.setSubject("Resultado - Pesquisa de pessoas.");
				
				
				StringBuilder stringBuilderCorpoEmail = new StringBuilder();

				stringBuilderCorpoEmail.append("<h2>Olá, <p>");
				stringBuilderCorpoEmail.append("<h2>Conforme solicitado, enviamos um arquivo com a lista de pessoas após sua pesquisa.</h2><p>");
				stringBuilderCorpoEmail.append("<br/><h2 style=\"font-family:Trebuchet MS;\">Att, </h2><p>");
				stringBuilderCorpoEmail.append("<h2 style=\"font-family:Trebuchet MS;\">Gustavo da Silva Paulo</h2>");
				stringBuilderCorpoEmail.append("<h2 style=\"font-family:Trebuchet MS;\">Contato: (13) 98228-8624</h2>");
				stringBuilderCorpoEmail.append("<h2 style=\"font-family:Trebuchet MS;\">Portfólio: <a target=\"_blank\" href=\"https://portfoliojava.herokuapp.com\">portfoliojava.herokuapp.com</a></h2>");

				MimeBodyPart corpoEmailPdf = new MimeBodyPart();
				
				corpoEmail = stringBuilderCorpoEmail.toString();
				corpoEmailPdf.setContent(corpoEmail, "text/html; charset=utf-8");

				
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(corpoEmailPdf);
				
				MimeBodyPart anexoEmail = new MimeBodyPart();

				anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(pdf, "application/pdf")));
				anexoEmail.setFileName("relatorio.pdf");

				multipart.addBodyPart(anexoEmail);
				
				message.setContent(multipart);

				
				Transport.send(message);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
}