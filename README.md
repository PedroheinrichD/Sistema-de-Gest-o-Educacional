# Sistema-de-Gest-o-Educacional

## Organização em camadas

O código Java está separado nos pacotes:

- `sge.model`: entidades, autenticação e geração polimórfica de relatórios.
- `sge.repository`: repositórios genéricos e implementação temporária em memória.
- `sge.service`: regras e composição dos relatórios.
- `sge.ui`: menu interativo de relatórios.
- `sge`: classe `Main`, responsável por integrar as camadas.

As senhas dos usuários são armazenadas como hashes PBKDF2 com salt aleatório.
As credenciais e entidades inicializadas em `Main` são demonstrativas.

Para compilar e executar a demonstração com o JDK instalado, no PowerShell:

```powershell
New-Item -ItemType Directory -Force out
javac -d out (Get-ChildItem -Recurse -Filter *.java src\main\java | ForEach-Object { $_.FullName })
java -cp out sge.Main
```