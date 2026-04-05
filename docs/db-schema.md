# Oracle Schema

Run order:

1. Connect to `192.168.219.105:1521` with service name `xepdb1`
2. Login as `fm / oracle`
3. In SQL*Plus/sqlcl run [create_schema.sql](/C:/Dev/WorkSpace/fmBoot/scripts/db/create_schema.sql)
4. In IntelliJ/DataGrip run [create_schema_ide.sql](/C:/Dev/WorkSpace/fmBoot/scripts/db/create_schema_ide.sql)
5. Run [fm_seed_dml.sql](/C:/Dev/WorkSpace/fmBoot/scripts/db/generated/fm_seed_dml.sql)

Created objects:

- Tables: `member`, `image`, `place`, `team`, `gmatch`, `teamblog`, `cment`, `notice`, `payment`, `reservation`
- Sequences: `seq_image`, `place_seq`, `team_seq`, `mno_seq`, `tb_no`, `cmt_seq`, `n_no_seq`, `reservation_seq`

Connection values:

- Host: `192.168.219.105`
- Port: `1521`
- Service name: `xepdb1`
- User: `fm`
- Password: `oracle`
