# Sistema-de-Gest-o-Educacional

## Entidades iniciais

O projeto contém as entidades `Aluno`, `Professor` e `Administrador`, que
herdam os dados comuns de `Usuario` e implementam a interface `Autenticacao`.
As senhas são armazenadas como hashes PBKDF2 com salt aleatório; o exemplo em
`Main` usa credenciais demonstrativas e não deve ser usado como configuração
de produção. O curso do aluno é representado por uma referência à entidade
`Curso`. `Aluno`, `Professor` e `Curso` implementam `GeradorRelatorio`, e a
demonstração oferece um menu para gerar vários relatórios em sequência.

Para compilar e executar a demonstração com o JDK instalado, no PowerShell:

```powershell
New-Item -ItemType Directory -Force out
javac -d out src\main\java\sge\*.java
java -cp out sge.Main
```