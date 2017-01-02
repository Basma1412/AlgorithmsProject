/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.project;

/**
 *
 * @author taseneem 21
 */
class Message {

    int sender_id;
    int receiver_id;
    int sequence_num;
    String message;

    public Message(int sender_id, int receiver_id, int sequence_num) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.sequence_num = sequence_num;
    }

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSender_id() {
        return this.sender_id;
    }

    public int getReceiver_id() {
        return this.receiver_id;
    }

    public int getSequence_num() {
        return this.sequence_num;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public void setSequence_num(int seqeunce) {
        this.sequence_num = seqeunce;
    }

}
