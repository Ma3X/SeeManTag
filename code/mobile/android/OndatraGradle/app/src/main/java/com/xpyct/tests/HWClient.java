/*
    Copyright (c) 2007-2014 Contributors as noted in the AUTHORS file

    This file is part of 0MQ.

    0MQ is free software; you can redistribute it and/or modify it under
    the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 3 of the License, or
    (at your option) any later version.

    0MQ is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.xpyct.tests;


//
//  Hello World client in Java
//  Connects REQ socket to tcp://localhost:5555
//  Sends "Hello" to server, expects "World" back
//

// https://github.com/zeromq/jeromq/blob/master/src/test/java/guide/hwclient.java
import org.zeromq.ZMQ;

public class HWClient {

    public String getInfo(String txt, String sendText) {
        String ret = "";
        ZMQ.Context context = ZMQ.context(1);

        //  Socket to talk to server
        System.out.println("Connecting to hello world server");

        ZMQ.Socket socket = context.socket(ZMQ.REQ);
        //socket.connect ("tcp://localhost:5555");
        //socket.connect ("tcp://192.168.1.1:5555");
        socket.connect("tcp://" + txt + ":3333");

        System.out.println("Sending: " + sendText);
        socket.send(sendText.getBytes (ZMQ.CHARSET), 0);
        byte[] reply = socket.recv(0);
        ret = new String (reply, ZMQ.CHARSET);
        System.out.println("Received: " + ret);

        //for(int requestNbr = 0; requestNbr != 10; requestNbr++) {
        //    String request = "Hello" ;
        //    System.out.println("Sending Hello " + requestNbr );
        //    socket.send(request.getBytes (ZMQ.CHARSET), 0);
        //
        //    byte[] reply = socket.recv(0);
        //    System.out.println("Received " + new String (reply, ZMQ.CHARSET) + " " + requestNbr);
        //}

        socket.close();
        context.term();

        return ret;
    }

    public static String main (String[] args){
        return new HWClient().getInfo(args[0], args[1]);
    }
}
