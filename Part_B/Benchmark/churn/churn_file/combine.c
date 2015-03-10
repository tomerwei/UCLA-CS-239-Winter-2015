#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_FILE 2000
#define MAX_NAME 500

#define MAX_BUFFER 2000

char buffer[MAX_BUFFER];

int churn_count = 0;

char tmp[MAX_NAME];


struct churn_data
{
	char name[MAX_NAME];
	int tloc;
	int cloc;
	int dloc;
	int committer;
	double comment;
	int function_calls;

} churn_data[MAX_FILE];

//argv[1] churn data
//argv[2] committer data
//argv[3] my data
//argv[4] output file
int main(int argc, char * argv[])
{
	FILE * churn = fopen(argv[1], "r");
	FILE * comm = fopen(argv[2], "r");
	FILE * my = fopen(argv[3], "r");
	FILE * output = fopen(argv[4], "w");
	int count = 0, committer = 0, ph = 0, call = 0, total = 0;
	double dph = 0, comments = 0;

	while (fscanf(churn, "%s %d %d %d", &churn_data[churn_count].name, &churn_data[churn_count].tloc, &churn_data[churn_count].cloc,
			&churn_data[churn_count].dloc) != EOF)
			{
				if (!strstr(churn_data[churn_count].name, ".java")) churn_count--;
				churn_count++;
			}



	while (fscanf(comm, "%s %d", &tmp, &committer) != EOF)
	{
		while (strcmp(churn_data[count].name, tmp) < 0) churn_data[count++].committer = 1;
		if (!strcmp(churn_data[count].name, tmp)) churn_data[count++].committer = committer;
	}

	count = 0;

	while (fgets(buffer, MAX_BUFFER, my))
	{
		sscanf(buffer, "%s %d %d %lf %d %lf", tmp, &ph, &ph, &dph, &call, &comments);
		fprintf(stdout, "%s %d %lf\n", tmp, call, comments);

		for (count = 0; count < churn_count; count ++)
		if (!strcmp(churn_data[count].name, tmp))
		{
			churn_data[count].comment = comments;
			churn_data[count].function_calls = call;
			count++;
		}
	}

	fprintf(output, "file_name total_loc churned_loc, deleted_loc, committer, comment, function_calls\n");
	for (count = 0; count < churn_count;
			 fprintf(output, "%s %d %d %d %d %lf %d\n", churn_data[count].name, churn_data[count].tloc, churn_data[count].cloc,
					 churn_data[count].dloc, churn_data[count].committer, churn_data[count].comment, churn_data[count].function_calls), count++);
	fclose(output);
}
