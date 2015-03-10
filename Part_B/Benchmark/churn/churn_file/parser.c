#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LENGTH_SIZE 1000
#define MAX_FILE  5000
char buffer[MAX_LENGTH_SIZE];
char file_name[MAX_LENGTH_SIZE];
char same[MAX_LENGTH_SIZE];
char modified[MAX_LENGTH_SIZE];
char added[MAX_LENGTH_SIZE];
char removed[MAX_LENGTH_SIZE];
char temp[MAX_LENGTH_SIZE];

int data_count  = 0;

struct tuple
{
  char name[MAX_LENGTH_SIZE];
  int total_loc;
  int churned_loc;
  int deleted_loc;
} data[MAX_FILE];


int state = 0;

void myqsort(int i, int j)
{
	int i1 = i, j1 = j;
	strcpy(temp, data[(i + j)/2].name);
	while (i < j)
	{
		while (strcmp(data[i].name, temp) < 0) i++;
		while (strcmp(data[j].name, temp) > 0) j--;
		if (i <= j)
		{
			struct tuple tmp = data[i];
			data[i++] = data[j]; data[j--] = tmp;
		}
	}
	if (i < j1) myqsort(i, j1);
	if (i1 < j) myqsort(i1, j);
}

//argv[1] old file dir
int main(int argc, char * argv[])
{
        int i = 0;
	 
	printf("file_name total_loc churned_loc deleted_loc\n");
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
					fprintf(stderr, "%s\n", argv[1]);
					if (strstr(file_name, "java") && !strstr(file_name, argv[1]))
					{

						int blank = 0, comment = 0, code = 0;
						char * ptr = strstr(file_name, "/src"); ptr++;
						sscanf(same, "%s %d %d %d", &temp, &blank, &comment, &code);
						if (code)
						{
							    fprintf(stderr, "%d\n", data_count);
							int total_loc = 0, churned_loc = 0, deleted_loc = 0;
							total_loc += code;

							sscanf(modified, "%s %d %d %d", &temp, &blank, &comment, &code);
							total_loc += code; churned_loc += code;

							sscanf(added, "%s %d %d %d",  &temp, &blank, &comment, &code);
							churned_loc += code;

							sscanf(removed, "%s  %d %d %d", &temp, &blank, &comment, &code);
							total_loc += code; deleted_loc += code;

							data_count++;
							strcpy(data[data_count].name, ptr);
							data[data_count].total_loc = total_loc;
                            data[data_count].churned_loc = churned_loc;
 							data[data_count].deleted_loc = deleted_loc; 

						}
					}
				}
		}

	}
exit:
    fprintf(stderr, "%d\n", data_count);
    myqsort(0, data_count-1);
    for (i = 0; i < data_count; printf("%s %d %d %d\n", data[i].name, data[i].total_loc, data[i].churned_loc, data[i].deleted_loc), i++); 
    return 0;
}
