%{
#include<stdio.h>
%}

%%

stop exit(0);

[+] {printf("\nADDITION");}
[-] {printf("\nSUBTRACTION");}
[*] {printf("\nMULTIPLICATION");}
[/] {printf("\nDIVISION");}
[|] {printf("\nABS");}
[0-9]+ {printf("\nNumber: %s",yytext);}
\n {printf("\nNew Line");}
.  {printf("\nMystery Character: %s",yytext);}

%%

main()
{
yylex();
}


