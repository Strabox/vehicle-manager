package com.pt.pires.services.integrator;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import com.pt.pires.domain.UserRole;
import com.pt.pires.domain.exceptions.InitializationFileCorruptedException;
import com.pt.pires.domain.exceptions.VehicleManagerException;
import com.pt.pires.services.local.INoteService;
import com.pt.pires.services.local.INotificationTaskService;
import com.pt.pires.services.local.IRegistrationService;
import com.pt.pires.services.local.IUserService;
import com.pt.pires.services.local.IVehicleService;

@Service("importIntegratorService")
public class ImportIntegratorService implements IImportIntegratorService{

	private static final String CONFIG_FILE_PATH = "/other/Initialize.xml";
	
	private static final String CONFIG_XSD_FILE_PATH = "/other/InitializeXSD.xml";
	
	@Autowired
	@Qualifier("vehicleService")
	private IVehicleService vehicleService;
	
	@Autowired
	@Qualifier("userService")
	private IUserService userService;
	
	@Autowired
	@Qualifier("registrationService")
	private IRegistrationService registrationService;
	
	@Autowired
	@Qualifier("noteService")
	private INoteService noteService;
	
	@Autowired
	@Qualifier("notificationService")
	private INotificationTaskService notificationTaskService;
	
	
	@Override
	@Transactional(readOnly = false)
	public void importFrom() throws VehicleManagerException {
		try{
			Resource resource = new ClassPathResource(CONFIG_FILE_PATH);
			InputStream resourceInputStream = resource.getInputStream();
			SAXBuilder saxBuilder = new SAXBuilder();
			Document doc = saxBuilder.build(resourceInputStream);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			Element root = doc.getRootElement();
			Element usersElem = root.getChild("users");
			for(Element userElem : usersElem.getChildren()){
				String username = userElem.getChild("username").getText();
				String plainPassword = userElem.getChild("password").getText();
				String email = userElem.getChild("email").getText();
				String role = userElem.getAttributeValue("role");
				userService.createUser(username, plainPassword,email,UserRole.valueOf(role));
			}
			
			Element vehiclesElem = root.getChild("vehicles");
			for(Element vehicleElem : vehiclesElem.getChildren()){
				String vehicleType = vehicleElem.getName();
				String vehicleName = vehicleElem.getAttributeValue("name");
				String brand = vehicleElem.getAttributeValue("brand");
				Date acquisitionDate = formatter.parse(vehicleElem.getAttributeValue("acquisitionDate"));
				if(vehicleType.equals("vehicleUnlicensed")){
					int fabricationYear = Integer.parseInt(vehicleElem.getAttributeValue("fabricationYear"));
					vehicleService.createUnlicensedVehicle(vehicleName, brand, acquisitionDate, fabricationYear);
				}
				else if(vehicleType.equals("vehicleLicensed")){
					Element licenseElem = vehicleElem.getChild("license");
					String license = licenseElem.getText();
					Date licenseDate = formatter.parse(licenseElem.getAttributeValue("date"));
					vehicleService.createLicensedVehicle(vehicleName, brand, acquisitionDate, license, licenseDate);
				}
				Element regsElem = vehicleElem.getChild("registrations");
				for(Element regElem : regsElem.getChildren()){
					Long time = Long.parseLong(regElem.getAttributeValue("time"));
					Date date = formatter.parse(regElem.getAttributeValue("date"));
					String description = regElem.getText();
					registrationService.createRegistration(vehicleName, time, description, date);
				}
				Element notesElem = vehicleElem.getChild("notes");
				for(Element noteElem : notesElem.getChildren()){
					noteService.createNote(vehicleName, noteElem.getText());
				}
				Element notisElem = vehicleElem.getChild("notifications");
				for(Element notiElem : notisElem.getChildren()){
					Date notiDate = formatter.parse(notiElem.getAttributeValue("notiDate"));
					String description = notiElem.getText();
					notificationTaskService.createYearNotification(vehicleName, description, notiDate);
				}
			}
		}catch(IOException|JDOMException|ParseException e) {
			throw new InitializationFileCorruptedException();
		}
	}

	@Override
	public void validate() throws VehicleManagerException {
		Resource config = new ClassPathResource(CONFIG_FILE_PATH);
		Resource configXSD = new ClassPathResource(CONFIG_XSD_FILE_PATH);
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema;
        try {
        	schema = factory.newSchema(new StreamSource(configXSD.getFile()));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(config.getFile()));
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			throw new InitializationFileCorruptedException();
		}
	}

}
