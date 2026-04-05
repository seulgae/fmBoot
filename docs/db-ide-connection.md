# Oracle IDE Connection

Current project datasource:

- Host: `localhost`
- Port: `1521`
- Service name: `XEPDB1`
- User: `fm`
- Password: `oracle`
- JDBC URL: `jdbc:oracle:thin:@localhost:1521/XEPDB1`
- Driver: `oracle.jdbc.driver.OracleDriver`

IntelliJ / DataGrip setup:

1. Add a new Oracle data source.
2. Set `Host=localhost`, `Port=1521`, `Service=XEPDB1`.
3. Set `User=fm`, `Password=oracle`.
4. If the account is locked, run [open-fm-user.sql](/C:/Dev/WorkSpace/fmBoot/scripts/db/open-fm-user.sql) as `SYSTEM`.

Current environment check on April 5, 2026:

- `localhost:1521` is closed.
- No Oracle service/process is currently running on this machine.

Seed DML:

- Generator: [generate-seed-dml.ps1](/C:/Dev/WorkSpace/fmBoot/scripts/db/generate-seed-dml.ps1)
- Generated SQL: [fm_seed_dml.sql](/C:/Dev/WorkSpace/fmBoot/scripts/db/generated/fm_seed_dml.sql)

Generate again:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\db\generate-seed-dml.ps1
```
