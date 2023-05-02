import { Body, Controller, Get, Post } from '@nestjs/common';
import { QryService } from './qry.service';
import { BindList } from './qry.entity';
import { BindlistDto } from './dto/bindlist.dto';

@Controller('qry')
export class QryController {
  constructor(private readonly qryService: QryService) {}

  @Get()
  qryRandomBind(): Promise<BindList> {
    return this.qryService.randomQuery();
  }

  @Post()
  qryCustomBind(@Body() bindListDto: BindlistDto): Promise<BindList> {
    return this.qryService.customQuery(bindListDto);
  }
}
