package com.ux7.fullhyve.cases;

/**
 * Created by hp on 06/22/18.
 */

public class MessagingCase {

    public String senderUserName;
    public String senderPassword;
    public String receiverUserName;
    public String receiverPassword;
    public String message;

    public MessagingCase (String senderUserName, String senderPassword, String receiverUserName, String receiverPassword, String message) {

        this.senderPassword = senderPassword;
        this.receiverPassword = receiverPassword;
        this.receiverUserName = receiverUserName;
        this.senderUserName = senderUserName;
        this.message = message;

    }

}
