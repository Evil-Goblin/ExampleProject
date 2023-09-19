import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { QryRepository } from './qry.repository';
import { BindList } from './qry.entity';
import { BindlistDto } from './dto/bindlist.dto';

@Injectable()
export class QryService {
  constructor(
    @InjectRepository(QryRepository)
    private qryRepository: QryRepository,
  ) {}

  async customQuery(bindListDto: BindlistDto): Promise<BindList> {
    return this.qryRepository.customQuery(bindListDto);
  }

  async randomQuery(): Promise<BindList> {
    return this.qryRepository.randomQuery();
  }
}
