#ifndef REDIS_SERVER_H
#define REDIS_SERVER_H

#include <string>
#include <atomic>

class RedisServer{
    public:
        RedisServer(int port);
        void run();
        void shutdown();
        

    private:
        int port;
        int server_socket;
        std::atommic<bool> running
}

#end if