package lk.ijse.cozyrobes.controller;

import lk.ijse.cozyrobes.util.EmailUtil;

public class MailTest {
    public static void main(String[] args) {
        EmailUtil.sendSimpleMail("sahandanujaya48@gmail.com", "test", "test");
    }
}
