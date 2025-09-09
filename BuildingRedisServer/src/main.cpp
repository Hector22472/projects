#include <iostream>
#include "../include/RedisServer.h"
#include <string>

int main(int argc, char* argv[]){
    int port = 6379;
    if(argc >= 2){
        port = std::stoi(argv[1]);
    }

    RedisServer server();

    return 0;
}