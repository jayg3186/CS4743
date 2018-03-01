package controller;

import org.apache.logging.log4j.LogManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.AuditTrailEntry;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.collections.ObservableList;
import model.*;

public class AuditTrailController {
	@FXML
	
	private ListView<AuditTrailEntry> auditList;
	private TextField authorName;
	
	private static Logger logger = LogManager.getLogger(AuditTrailController.class);
	private List<AuditTrailEntry> auditEntry;
	
	public AuditTrailController(List<AuditTrailEntry> auditEntry){
		this.auditEntry = auditEntry;
	}
	
	public void initialize() {
		logger.error("Audit Trail Started");
		
	}
	
	
	
}
