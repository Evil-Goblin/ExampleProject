package pg.packet_generator.service;

import pg.packet_generator.domain.GenerateSpecification;

public interface SendService {

    void execute(GenerateSpecification generateSpecification);
}
