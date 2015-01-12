package com.xpyct.tests;

import android.os.Handler;

import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class ZeroMQServer implements Runnable {
    private final Handler uiThreadHandler;

    public ZeroMQServer(Handler uiThreadHandler) {
        this.uiThreadHandler = uiThreadHandler;
    }

    @Override
    public void run() {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.REP);
        //socket.bind("tcp://127.0.0.1:5555");
        socket.bind("tcp://*:5555");

        System.out.printf ("I: echo service is ready at %s\n", "tcp://*:5555");

        while (true) {
            ZMsg msg = ZMsg.recvMsg(socket);
            if (msg == null)
                break;          //  Interrupted
            msg.send(socket);
        }
        if (Thread.currentThread().isInterrupted())
            System.out.printf ("W: interrupted\n");

        //org.zeromq.ZContext ctx = new ZContext();;
        //ctx.destroy();

        //while(!Thread.currentThread().isInterrupted()) {
        //    byte[] msg = socket.recv(0);
        //    uiThreadHandler.sendMessage(
        //            Util.bundledMessage(uiThreadHandler, new String(msg)));
        //    socket.send(Util.reverseInPlace(msg), 0);
        //}
        socket.close();
        context.term();
    }
}
