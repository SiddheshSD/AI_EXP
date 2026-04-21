%{
#include<stdio.h>
%}

%%

stop exit(0);

username printf("\n%s",getlogin());

%%

main()
{
yylex();
}


