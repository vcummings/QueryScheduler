package com.budco.appReports.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.budco.appReports.helper.Email;

public class EmailUtil {

	final static String EMAIL_PROPERTIES = "C:\\QueryScheduler\\jobs\\cfg\\email.properties";

	public static void send(Email email) {
		String host = "mx.budco.com";

		// create some properties and get the default Session
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);

		Session session = Session.getInstance(props, null);

		try {
			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email.getFrom()));
			List<String> recipients = Arrays.asList(email.getAddress().split(" "));
			for (int i = 0; i < recipients.size(); i++) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipients.get(i)));
			}
			msg.setSubject(email.getSubject());

			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(email.getBody());

			// create the second message part
			MimeBodyPart mbp2 = new MimeBodyPart();

			// attach the file to the message
			FileDataSource fds = new FileDataSource(email.getFileName());
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());

			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);

			// add the Multipart to the message
			msg.setContent(mp);

			// set the Date: header
			msg.setSentDate(new Date());

			// send the message
			Transport.send(msg);

		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
		}
	}

	   public static void sendError(String emailText) {

		   	Properties props = PropertyUtil.loadProperties(EMAIL_PROPERTIES);
		   	String host = "mx.budco.com";

		   	Properties properties = System.getProperties();
		   	properties.setProperty("mail.smtp.host", host);

		   	Session session = Session.getDefaultInstance(properties);

		      try{
		         MimeMessage message = new MimeMessage(session);

		         message.setFrom(new InternetAddress(props.getProperty("emailFrom")));
		         message.addRecipient(Message.RecipientType.TO,
		                                  new InternetAddress(props.getProperty("emailTo")));
		         message.setSubject(props.getProperty("emailSubject"));
		         message.setText(emailText);

		         Transport.send(message);
		         System.out.println("Sent message successfully....");
		      }catch (MessagingException mex) {
		         mex.printStackTrace();
		      }
		   }}
