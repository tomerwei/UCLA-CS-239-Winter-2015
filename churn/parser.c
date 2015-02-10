#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LENGTH_SIZE 1000
char buffer[MAX_LENGTH_SIZE];
char file_name[MAX_LENGTH_SIZE];
char same[MAX_LENGTH_SIZE];
char modified[MAX_LENGTH_SIZE];
char added[MAX_LENGTH_SIZE];
char removed[MAX_LENGTH_SIZE];
char temp[MAX_LENGTH_SIZE];

int state = 0;

//argv[1] input file name
//argv[2] output file name
//argv[3] original file dir
int main(int argc, char * argv[])
{
	freopen(argv[1], "r", stdin);
	freopen(argv[2], "w", stdout);
	printf("file_name total_loc churned_loc deleted_loc churned/total deteled/total churned/deleted\n");
	while (gets(buffer))
	{
//		fprintf(stderr, "buffer = %s\n", buffer);
//		fprintf(stderr, "state = %d\n", state);
		switch (state)
		{
			case 0:
				if (strstr(buffer, "---")) state = 1;
				break;
			case 1:
				if (strstr(buffer, "---")) state = 2;
				break;
			case 2:
				if (strstr(buffer, "---")) goto exit;
				else
				{
					strcpy(file_name, buffer);
					gets(same); gets(modified); gets(added); gets(removed);
					if (strstr(file_name, "java") && strstr(file_name, argv[3]))
					{
						int blank = 0, comment = 0, code = 0;
						char * ptr = strrchr(file_name, '/'); ptr++;

						sscanf(same, "%s %d %d %d", &temp, &blank, &comment, &code);
//						fprintf(stderr, "%s\n", same);
//						fprintf(stderr, "%d\n", code);
						if (code)
						{
//							fprintf(stderr, "Passed second!\n");
							int total_loc = 0, churned_loc = 0, deleted_loc = 0;
							total_loc += code;

							sscanf(modified, "%s %d %d %d", &temp, &blank, &comment, &code);
							total_loc += code; churned_loc += code;

							sscanf(added, "%s %d %d %d",  &temp, &blank, &comment, &code);
							churned_loc += code;

							sscanf(removed, "%s  %d %d %d", &temp, &blank, &comment, &code);
							total_loc += code; deleted_loc += code;

							printf("%s %d %d %d %lf %lf %lf\n", ptr, total_loc, churned_loc, deleted_loc, churned_loc * 1.0/total_loc, deleted_loc * 1.0/total_loc, churned_loc * 1.0 /deleted_loc);

						}
					}
				}
		}

	}
exit:
 return 0;
}
