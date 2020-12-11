package roomRequirementBuilder;

/**
 * ItemFactory builds <code>RoomItem</code>.
 * @author Group 0168
 * @author CSC207 - Fall 2020
 */

public class ItemFactory {
    /**
     * Create a new <code>Projector</code> with identical price and series number.
     * @return a new projector.
     */
    public Projector constructProjector(){
        Projector projector = new Projector();
        projector.setPrice(20);
        projector.setSeriesNum("77231-237");
        return projector;
    }

    /**
     * Create a new <code>Microphone</code> with identical price and series number.
     * @return a new microphone.
     */
    public MicroPhone constructMicrophone(int quantity){
        MicroPhone microPhone = new MicroPhone(quantity);
        microPhone.setPrice(10);
        microPhone.setSeriesNum("mic213-23091");
        return microPhone;
    }

    /**
     * Create a new <code>PartyAudio</code> with identical price and series number.
     * @return a new party audio system.
     */
    public PartyAudioSystem constructPartyAudio(){
        PartyAudioSystem partyAudioSystem = new PartyAudioSystem();
        partyAudioSystem.setPrice(50);
        partyAudioSystem.setSeriesNum("Audio-213-866L");
        return partyAudioSystem;
    }

}
