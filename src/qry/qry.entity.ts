import { BaseEntity, Column, Entity, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class BindList extends BaseEntity {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ type: 'timestamp', default: () => 'CURRENT_TIMESTAMP' })
  OT_DATETIME: Date;

  @Column({ type: 'date', default: () => 'CURRENT_DATE' })
  OT_DATE: Date;

  @Column()
  OT_BOOLEAN: boolean;

  @Column()
  OT_INTEGER: number;

  @Column({ type: 'float' })
  OT_DOUBLE: number;

  @Column()
  OT_STRING: string;
}
