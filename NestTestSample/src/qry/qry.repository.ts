import { CustomRepository } from './decorator/typeorm-ex.decorator';
import { BindList } from './qry.entity';
import { Repository } from 'typeorm';
import { BindlistDto } from './dto/bindlist.dto';
import {
  randomBoolean,
  randomDate,
  randomDouble,
  randomInt,
  randomString,
} from '../util/random';

@CustomRepository(BindList)
export class QryRepository extends Repository<BindList> {
  async customQuery(bindListDto: BindlistDto): Promise<BindList> {
    const {
      OT_DATETIME,
      OT_DATE,
      OT_BOOLEAN,
      OT_INTEGER,
      OT_DOUBLE,
      OT_STRING,
    } = bindListDto;

    const bindList = this.create({
      OT_DATETIME,
      OT_DATE,
      OT_BOOLEAN,
      OT_INTEGER,
      OT_DOUBLE,
      OT_STRING,
    });

    await this.save(bindList);

    return bindList;
  }

  async randomQuery(): Promise<BindList> {
    const random_date = new Date(randomDate(new Date(1970, 0, 1), new Date()));
    const OT_DATETIME = random_date;
    const OT_DATE = random_date;
    const OT_BOOLEAN = randomBoolean();
    const OT_INTEGER = randomInt(10000);
    const OT_DOUBLE = randomDouble(10000);
    const OT_STRING = randomString();

    const bindList = this.create({
      OT_DATETIME,
      OT_DATE,
      OT_BOOLEAN,
      OT_INTEGER,
      OT_DOUBLE,
      OT_STRING,
    });

    await this.save(bindList);

    return bindList;
  }
}
