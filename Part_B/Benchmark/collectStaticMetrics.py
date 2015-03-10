import sys
import commands

suspiciousness = open("./reports/closure-compiler/method-scores.csv", 'r')

methods = dict()

methodCounts = dict()

lastClass = ""

#---------------

totalNumberOfLines = commands.getoutput("./lines_of_codes.sh closure-compiler")

output = totalNumberOfLines.split("\n")

for line in output:
	if "Size information" in line:
		classFile = line.split(" ")[-1]
		lastClass = classFile.split(".")[0]
	if "Method:" in line:
		numLines = line.split(" ")
		methodName = numLines[-2]
		numberOfLines = int(numLines[-1][1:-1])
		
		key = lastClass + "." + methodName
		
		if key in methods:
			methods[key]['numberOfLines'] = methods[key]['numberOfLines'] + numberOfLines
			methodCounts[key][0] = methodCounts[key][0] + 1
		
		else:
			methods[key] = dict()
			methods[key]['linesAdded'] = 0
			methods[key]['linesDeleted'] = 0
			methods[key]['numberOfLines'] = numberOfLines
			methodCounts[key] = [1,0,0]

#---------------

linesAdded = commands.getoutput("./number_of_lines_added.sh closure-compiler")

output = linesAdded.split("\n")

for line in output:
	if "Size Difference" in line:
		classFile = line.split(" ")[-1]
		lastClass = classFile.split(".")[0]
	if "in method" in line:
		lineAddition = line.split(" ")
		methodName = lineAddition[-1]
		numberOfLines = int(lineAddition[0])
		
		key = lastClass + "." + methodName
		
		if key in methods:
			methods[key]['linesAdded'] = methods[key]['linesAdded'] + numberOfLines
			methodCounts[key][1] = methodCounts[key][1] + 1

#---------------

linesDeleted = commands.getoutput("./number_of_lines_deleted.sh closure-compiler")

output = linesDeleted.split("\n")

for line in output:
	if "Size Difference" in line:
		classFile = line.split(" ")[-1]
		lastClass = classFile.split(".")[0]
	if "in method" in line:
		lineDeletion = line.split(" ")
		methodName = lineDeletion[-1]
		numberOfLines = int(lineDeletion[0])
		
		key = lastClass + "." + methodName
		
        if key in methods:
            methods[key]['linesDeleted'] = methods[key]['linesDeleted'] + numberOfLines
            methodCounts[key][2] = methodCounts[key][2] + 1

#---------------

for key in methodCounts:
	if methodCounts[key][0] != 0:
		methods[key]['numberOfLines'] = methods[key]['numberOfLines'] / methodCounts[key][0]
	if methodCounts[key][1] != 0:
		methods[key]['linesAdded'] = methods[key]['linesAdded'] / methodCounts[key][1]
	if methodCounts[key][2] != 0:
		methods[key]['linesDeleted'] = methods[key]['linesDeleted'] / methodCounts[key][2]
	
#---------------

finalcsv = open("method-scores-with-metrics-closure-compiler.csv", 'w')

finalcsv.write("Method_Name,Tarantula,Tarantula_Weighted,Sbi,Sbi_Weighted,Jaccard,Jaccard_Weighted,Ochiai,Ochiai_Weighted,Total_Number_Of_Lines,Number_Of_Lines_Added,Number_Of_Lines_Deleted\n")

for line in suspiciousness:

	methodKey = line[0:line.find("(")]
	
	methodKey = methodKey.split("/")[-1]
	
	methodKey = methodKey.replace("<init>", "Constructor", 1)
	
	classNames = methodKey.split(".")[0]
	classNames = classNames.replace(";","")

	methodName = methodKey.split(".")[1]

	for className in classNames.split("$"):    
		if (className+"."+methodName) in methods:
			finalcsv.write(line[:-1] + "," + str(methods[className+"."+methodName]['numberOfLines']) + "," + str(methods[className+"."+methodName]['linesAdded']) + "," + str(methods[className+"."+methodName]['linesDeleted']) + "\n")
			break

suspiciousness.close()
finalcsv.close()

#------------------------------------------------------------------------------------------------

suspiciousness = open("./reports/joda-time/method-scores.csv", 'r')

methods = dict()

methodCounts = dict()

lastClass = ""

#---------------

totalNumberOfLines = commands.getoutput("./lines_of_codes.sh joda-time")

output = totalNumberOfLines.split("\n")

for line in output:
	if "Size information" in line:
		classFile = line.split(" ")[-1]
		lastClass = classFile.split(".")[0]
	if "Method:" in line:
		numLines = line.split(" ")
		methodName = numLines[-2]
		numberOfLines = int(numLines[-1][1:-1])
		
		key = lastClass + "." + methodName
		
		if key in methods:
			methods[key]['numberOfLines'] = methods[key]['numberOfLines'] + numberOfLines
			methodCounts[key][0] = methodCounts[key][0] + 1
		
		else:
			methods[key] = dict()
			methods[key]['linesAdded'] = 0
			methods[key]['linesDeleted'] = 0
			methods[key]['numberOfLines'] = numberOfLines
			methodCounts[key] = [1,0,0]

#---------------

linesAdded = commands.getoutput("./number_of_lines_added.sh joda-time")

output = linesAdded.split("\n")

for line in output:
	if "Size Difference" in line:
		classFile = line.split(" ")[-1]
		lastClass = classFile.split(".")[0]
	if "in method" in line:
		lineAddition = line.split(" ")
		methodName = lineAddition[-1]
		numberOfLines = int(lineAddition[0])
		
		key = lastClass + "." + methodName
		
		if key in methods:
			methods[key]['linesAdded'] = methods[key]['linesAdded'] + numberOfLines
			methodCounts[key][1] = methodCounts[key][1] + 1

#---------------

linesDeleted = commands.getoutput("./number_of_lines_deleted.sh joda-time")

output = linesDeleted.split("\n")

for line in output:
	if "Size Difference" in line:
		classFile = line.split(" ")[-1]
		lastClass = classFile.split(".")[0]
	if "in method" in line:
		lineDeletion = line.split(" ")
		methodName = lineDeletion[-1]
		numberOfLines = int(lineDeletion[0])
		
		key = lastClass + "." + methodName
		
        if key in methods:
            methods[key]['linesDeleted'] = methods[key]['linesDeleted'] + numberOfLines
            methodCounts[key][2] = methodCounts[key][2] + 1

#---------------

for key in methodCounts:
	if methodCounts[key][0] != 0:
		methods[key]['numberOfLines'] = methods[key]['numberOfLines'] / methodCounts[key][0]
	if methodCounts[key][1] != 0:
		methods[key]['linesAdded'] = methods[key]['linesAdded'] / methodCounts[key][1]
	if methodCounts[key][2] != 0:
		methods[key]['linesDeleted'] = methods[key]['linesDeleted'] / methodCounts[key][2]
	
#---------------

finalcsv = open("method-scores-with-metrics-joda-time.csv", 'w')

finalcsv.write("Method_Name,Tarantula,Tarantula_Weighted,Sbi,Sbi_Weighted,Jaccard,Jaccard_Weighted,Ochiai,Ochiai_Weighted,Total_Number_Of_Lines,Number_Of_Lines_Added,Number_Of_Lines_Deleted\n")

for line in suspiciousness:

	methodKey = line[0:line.find("(")]
	
	methodKey = methodKey.split("/")[-1]
	
	methodKey = methodKey.replace("<init>", "Constructor", 1)
	
	classNames = methodKey.split(".")[0]
	classNames = classNames.replace(";","")

	methodName = methodKey.split(".")[1]

	for className in classNames.split("$"):    
		if (className+"."+methodName) in methods:
			finalcsv.write(line[:-1] + "," + str(methods[className+"."+methodName]['numberOfLines']) + "," + str(methods[className+"."+methodName]['linesAdded']) + "," + str(methods[className+"."+methodName]['linesDeleted']) + "\n")
			break

suspiciousness.close()
finalcsv.close()
