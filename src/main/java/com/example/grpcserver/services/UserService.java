package com.example.grpcserver.services;


import com.example.grpcserver.entities.UserEntity;
import com.example.grpcserver.repositories.UserRepository;
import com.google.protobuf.Timestamp;
import in.haxxon420.grpc.models.User;
import in.haxxon420.grpc.models.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void register(User request, StreamObserver<User> responseObserver) {
        if (request.getName().isEmpty() || request.getMobile().isEmpty()) {
            responseObserver.onError(new IllegalArgumentException("Name is required"));
        }
        var createdUser =
                userRepository.save(UserEntity.builder().name(request.getName()).mobile(request.getMobile()).role(request.getRole()).build());
        responseObserver.onNext(User.newBuilder().setId(createdUser.getId()).setName(createdUser.getName()).setMobile(createdUser.getMobile()).build());
        responseObserver.onCompleted();
    }

    @Override
    public void login(User request, StreamObserver<User> responseObserver) {
        super.login(request, responseObserver);
    }

    @Override
    public void updateLastSeenAt(Timestamp request, StreamObserver<User> responseObserver) {
        super.updateLastSeenAt(request, responseObserver);
    }
}
