package roomRequirementBuilder;

public class ItemFactory {

    public Projector constructProjector(){
        Projector projector = new Projector();
        projector.setPrice(20);
        projector.setSeriesNum("77231-237");
        return projector;
    }

    public MicroPhone constructMicrophone(int quantity){
        MicroPhone microPhone = new MicroPhone(quantity);
        microPhone.setPrice(10);
        microPhone.setSeriesNum("mic213-23091");
        return microPhone;
    }

    public PartyAudioSystem constructPartyAudio(){
        PartyAudioSystem partyAudioSystem = new PartyAudioSystem();
        partyAudioSystem.setPrice(50);
        partyAudioSystem.setSeriesNum("Audio-213-866L");
        return partyAudioSystem;
    }

}
