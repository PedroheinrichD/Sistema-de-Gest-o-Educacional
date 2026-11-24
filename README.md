# Sistema-de-Gest-o-Educacional

## Entidades iniciais

O projeto contém as entidades `Aluno`, `Professor` e `Curso`, com validação dos
atributos obrigatórios nos construtores. O curso do aluno é representado por uma
referência à entidade `Curso`.

Para compilar e executar a demonstração com o JDK instalado, no PowerShell:

```powershell
New-Item -ItemType Directory -Force out
javac -d out src\main\java\sge\*.java
java -cp out sge.Main
```