#include "include/RedisServer.h"

#include <iostream>
#include <system_error>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>

static RedisServer* globalServer = nullptr;

RedisServer::RedisServer(int port) : port(port), server_socket(-1), running(true){
    globalServer = this;
}

void RedisServer::shutdown(){
    running = false;
    if(server_socket != -1){
        close(server_socket);
        server_socket = -1;
    }

    std::cout << "Redis server shutting down..." << std::endl;
}

void RedisServer::run(){
    server_socket = socket(AF_INET, SOCK_STREAM, 0);
    if(server_socket == -1){
        std::cerr << "Error creating socket: " << std::system_error(errno, std::generic_category()).what() << std::endl;
        return;
    }

    int opt = 1;
    setsockopt(server_socket, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt))

    sockaddr_in serverAddr();
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_port = htons(port);
    serverAddr.sin_addr.s_addr = INADDR_ANY;

    if (bind(server_socket, (struct sockaddr*)&serverAddr, sizeof(serverAddr)) == -1) {
        std::cerr << "Error binding socket: " << std::system_error(errno, std::generic_category()).what() << std::endl;
        close(server_socket);
        return;
    }

    if (listen(server_socket, SOMAXCONN) == -1) {
        std::cerr << "Error listening on socket: " << std::system_error(errno, std::generic_category()).what() << std::endl;
        close(server_socket);
        return;
    }

    std::cout << "Redis server is running on port " << port << std::endl;

    while (running) {
        sockaddr_in clientAddr;
        socklen_t clientAddrLen = sizeof(clientAddr);
        int client_socket = accept(server_socket, (struct sockaddr*)&clientAddr, &clientAddrLen);
        if (client_socket == -1) {
            std::cerr << "Error accepting connection: " << std::system_error(errno, std::generic_category()).what() << std::endl;
            continue;
        }

        // Handle client connection in a separate thread or process
        close(client_socket);
    }

    close(server_socket);
}