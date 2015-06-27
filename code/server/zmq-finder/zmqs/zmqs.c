#include <stdio.h>

#define _WIN32_WINNT 0x0501
//#pragma comment(lib, "libws2_32.a")
#define ZMQ_STATIC
#include "zmq.h"

#define sleep(x) Sleep(1000 * x)

int main (int argc, char const *argv[])
{
    int major, minor, patch;
    zmq_version(&major, &minor, &patch);
    printf("Installed ZeroMQ version: %d.%d.%d\n", major, minor, patch);

    void* context = zmq_ctx_new();
    void* respond = zmq_socket(context, ZMQ_REP);

    zmq_bind(respond, "tcp://*:3333");
    printf("Starting...\n");

    for(;;)
    {
        zmq_msg_t request;
        zmq_msg_init(&request);
        int res = zmq_msg_recv(&request, respond, 0);
        if(res == -1) break;

        //-unsigned int type = request[0];
        //quint8 type = request.[0];
        //res = zmq_getsockopt(respond, ZMQ_RCVMORE, &more, &more_size);
        //if(!more) return false;

        char *data;
        int data_size;

        data = (char*) zmq_msg_data(&request);
        data_size = zmq_msg_size(&request);
        data[data_size] = '\0';

        //zmq_msg_t part;
        //switch(type) {
        //    case ZMQ_TYPE_STRING:
        //        zmq_msg_init(&part);
        //        zmq_msg_recv(&part, sub, 0);
        //        data = (char*) zmq_msg_data(&part);
        //        data_size = zmq_msg_size(&part);
        //        data[data_size] = '\0';
                //str = QString(data);
        //        zmq_msg_close(&part);
        //        break;
            //case ZMQ_TYPE_DATA:
            //    zmq_msg_init(&part);
            //    zmq_msg_recv(&part, sub, 0);
            //    data = (char*) zmq_msg_data(&part);
            //    data_size = zmq_msg_size(&part);
            //    ba = QByteArray(data, data_size);
            //    if(ba.size() < 32) qDebug() << "DATA:" << ba.toHex() << "size=" << data_size;
            //    else qDebug() << "DATA: LARGE" << "size=" << data_size; zmq_msg_close(&part); break;
        //}

        printf("Received: %s\n", data);
        zmq_msg_close(&request);
        sleep(1); // sleep one second
        zmq_msg_t reply;
        zmq_msg_init_size(&reply, strlen("world"));
        memcpy(zmq_msg_data(&reply), "world", 5);
        zmq_msg_send(&reply, respond, 0);
        zmq_msg_close(&reply);
    }
    zmq_close(respond);
    zmq_ctx_destroy(context);

    return 0;
}
