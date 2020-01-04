package solfege;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "exos")
@SessionScoped
public class ExercicesController {

	private static final String[] notes = {"DO", "RE", "MI", "FA", "SOL", "LA", "SI"};
	private static final String[] conditions = {"Avant", "Après"};
	
	public String[] notesMelange;
	public String noteATrouve;
	public String conditionNote;
	
	public int nbSuccess = 0;
	public int nbFailure = 0;
	
	@PostConstruct
	public void init() {
		mixNote();
		selectNote();
	}
	
	public String[] getNotesMelange() {
		return notesMelange;
	}

	public void setNotesMelange(String[] notesMelange) {
		this.notesMelange = notesMelange;
	}

	public String getNoteATrouve() {
		return noteATrouve;
	}

	public void setNoteATrouve(String noteATrouve) {
		this.noteATrouve = noteATrouve;
	}

	public String getConditionNote() {
		return conditionNote;
	}

	public void setConditionNote(String conditionNote) {
		this.conditionNote = conditionNote;
	}

	public int getNbSuccess() {
		return nbSuccess;
	}

	public void setNbSuccess(int nbSuccess) {
		this.nbSuccess = nbSuccess;
	}

	public int getNbFailure() {
		return nbFailure;
	}

	public void setNbFailure(int nbFailure) {
		this.nbFailure = nbFailure;
	}

	public void mixNote() {
		List<String> strList = new ArrayList<>();
		for (String note : notes) {
			strList.add(note);
		}
		Collections.shuffle(strList);
		notesMelange = strList.toArray(new String[strList.size()]);
	}
	
	public void selectNote() {
		Random r=new Random();
        int randomNumber=r.nextInt(notes.length);
        noteATrouve = notesMelange[randomNumber];
        
        int randomNumber2=r.nextInt(conditions.length);
        conditionNote = conditions[randomNumber2];
	}
	
	public void checkResult(String result) {
		int indexNote = findIndex(noteATrouve);
		int indexResult = findIndex(result);
		
		if(conditionNote.equals(conditions[0])) {
			if( (indexNote == 0 && indexResult == 6) || (indexNote == indexResult + 1) ) {
				messageSuccess();
			}
			else {
				messageError();
			}
		} else {
			if( (indexNote == 6 && indexResult == 0) || (indexNote == indexResult - 1) ) {
				messageSuccess();
			} else {
				messageError();
			}
		}
			
		mixNote();
		selectNote();
		
	}
	
	public void messageSuccess() {
		nbSuccess++;
		FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage("Successful",  "Note trouvée !") );
	}
	
	public void messageError() {
		nbFailure++;
		FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage("Echec",  "Note non trouvée !") );
	}
	
	public int findIndex(String note) {
		for(int i=0; i<notes.length; i++) {
			if(notes[i].equals(note)) {
				return i;
			}
				
		}
		return -1;
		
	}
	
	public void printList() {
		for(String note : notes) {
			System.out.print(note + " ");
		}
		System.out.println();
	}
	
	public void recommencer() {
		nbFailure = 0;
		nbSuccess = 0;
		mixNote();
		selectNote();
	}
	
}
