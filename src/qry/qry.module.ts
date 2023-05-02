import { Module } from '@nestjs/common';
import { TypeOrmExModule } from './decorator/typeorm-ex.module';
import { QryRepository } from './qry.repository';
import { QryController } from './qry.controller';
import { QryService } from './qry.service';

@Module({
  imports: [TypeOrmExModule.forCustomRepository([QryRepository])],
  controllers: [QryController],
  providers: [QryService],
})
export class QryModule {}
