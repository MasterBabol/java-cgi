#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <libgen.h>
char *remove_ext (char* mystr, char dot, char sep)
{
    char *retstr, *lastdot, *lastsep;

    if (mystr == NULL)
        return NULL;
    if ((retstr = malloc (strlen (mystr) + 1)) == NULL)
        return NULL;

    strcpy (retstr, mystr);
    lastdot = strrchr (retstr, dot);
    lastsep = (sep == 0) ? NULL : strrchr (retstr, sep);

    if (lastdot != NULL)
	{
        if (lastsep != NULL)
		{
            if (lastsep < lastdot)
                *lastdot = '\0';
		}
		else
			*lastdot = '\0';
    }
    return retstr;
}

char hex_table[] = "0123456789ABCDEF";
char* env_url_encode(char* src)
{
	int i, j = strlen(src), k;
	char* retbuf;
	
	for(i = 0; i < j; i++)
	{
		if (src[i] == '=')
		{
			retbuf = (char*)malloc(j * 3 + 1);
			memcpy(retbuf, src, ++i);
			for(k = i; k < j; k++)
			{
				if ((src[k] >= (char)'a' && src[k] <= (char)'z') || (src[k] >= (char)'A' && src[k] <= (char)'Z') || (src[k] >= (char)'0' && src[k] <= (char)'9'))
				{
					retbuf[i++] = src[k];
				}
				else
				{
					char chr = src[k];
					retbuf[i++] = '%';
					retbuf[i++] = hex_table[(chr & 0xFF) / 16];
					retbuf[i++] = hex_table[(chr & 0xFF) % 16];
				}
			}
			return retbuf;
		}
	}
	return src;
}

int main(int argc, char* argv[], char* envp[])
{
	if (argc > 1)
	{
		if (access(argv[1], R_OK) != -1)
		{
			int i, j;
			char* className = remove_ext(basename(argv[1]), '.', '/');
			char** newEnvp;
			chdir(dirname(argv[1]));
			
			for(i = 0; envp[i]; i++);
			newEnvp = (char**)malloc(sizeof(char*) * (i + 1));
			for(j = 0; j < i; j++)
			{
				if(strncmp(envp[j], "BOOTCLASSPATH", 13) == 0)
					newEnvp[j] = envp[j];
				else
					newEnvp[j] = env_url_encode(envp[j]);
			}
			newEnvp[j] = 0;
			execle("/usr/bin/jamvm", "/usr/bin/jamvm", className, 0, newEnvp);
		}
		else
		{
			puts("Status: 404 Not Found\n");
		}
	}
	return 0;
}
