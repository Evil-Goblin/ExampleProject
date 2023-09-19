import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { typeORMConfig } from './configs/typeorm.config';
import { QryModule } from './qry/qry.module';

@Module({
  imports: [TypeOrmModule.forRoot(typeORMConfig), QryModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
