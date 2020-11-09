package DTO;
   
/**
 * 
 * @author josef
 */
public class CombinedDTO {
     private String dadID; 
     private String dadValue; 
     private String chuckID; 
     private String chuckValue; 

    public CombinedDTO(ChuckDTO chuckDTO, DadDTO dadDTO) {
        this.dadID = dadDTO.getId();
        this.dadValue = dadDTO.getValue();
        this.chuckID = chuckDTO.getId();
        this.chuckValue = chuckDTO.getValue();
    }

    public String getDadID() {
        return dadID;
    }

    public void setDadID(String dadID) {
        this.dadID = dadID;
    }

    public String getDadValue() {
        return dadValue;
    }

    public void setDadValue(String dadValue) {
        this.dadValue = dadValue;
    }

    public String getChuckID() {
        return chuckID;
    }

    public void setChuckID(String chuckID) {
        this.chuckID = chuckID;
    }

    public String getChuckValue() {
        return chuckValue;
    }

    public void setChuckValue(String chuckValue) {
        this.chuckValue = chuckValue;
    }
     
     
}
