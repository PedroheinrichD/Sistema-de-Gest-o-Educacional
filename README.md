# Sistema-de-Gest-o-Educacional

## Organização em camadas

O código Java está separado nos pacotes:

- `sge.model`: entidades, autenticação e geração polimórfica de relatórios.
- `sge.repository`: repositórios genéricos e implementação temporária em memória.
- `sge.service`: regras de gestão acadêmica e composição dos relatórios.
- `sge.ui`: menu interativo para cadastros, turmas, avaliações e relatórios.
- `sge`: classe `Main`, responsável por integrar as camadas.

As senhas dos usuários são armazenadas como hashes PBKDF2 com salt aleatório.
Os dados são mantidos apenas em memória durante a execução.

Para compilar e executar o menu com o JDK instalado, no PowerShell:

```powershell
New-Item -ItemType Directory -Force out
javac -d out (Get-ChildItem -Recurse -Filter *.java src\main\java | ForEach-Object { $_.FullName })
java -cp out sge.Main
```

O menu permite cadastrar alunos, professores e cursos, criar turmas com
professor responsável, matricular alunos, registrar avaliações e gerar um
relatório consolidado. Para executar os testes de sucesso e falha:

```powershell
javac -cp out -d out (Get-ChildItem -Recurse -Filter *.java src\test\java | ForEach-Object { $_.FullName })
java -cp out sge.service.GestaoEducacionalServiceTest
```