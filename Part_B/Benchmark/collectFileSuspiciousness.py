import sys

file = open("reports/closure-compiler/method-scores.csv", 'r')

javaFiles = dict()

javaFileCounts = dict()

for line in file:
    fileKey = line[0:line.find("(")]
        
    fileKey = fileKey.split("/")[-1]
    
    fileKey = fileKey.split(".")[0]

    fileKey = fileKey.replace(";","")
    
    if '$' in fileKey:
        fileKey = fileKey.split("$")[0]

    scores = line.split(",")[1:]

    if fileKey in javaFiles:
        for i in range(8):
            javaFiles[fileKey][i] = javaFiles[fileKey][i] + float(scores[i])

        javaFileCounts[fileKey] = javaFileCounts[fileKey] + 1

    else:
        
        javaFiles[fileKey] = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        for i in range(8):
            javaFiles[fileKey][i] = float(scores[i])
        
        javaFileCounts[fileKey] = 1

file.close()

metricsFile = open("reports/closure-compiler/fin_closure", 'r')
fileMetrics = dict()

for line in metricsFile:
    fileName = line.split(" ")[0]
    fileName = fileName.split("/")[-1]
    fileName = fileName.split(".")[0]
    fileMetrics[fileName] = line.split(" ")[1:]

metricsFile.close()

file = open("./file-scores-closure-compiler.csv", 'w')

file.write("file_name,Tarantula,Tarantula_Weighted,Sbi,Sbi_Weighted,Jaccard,Jaccard_Weighted,Ochiai,Ochiai_Weighted,total_loc,churned_loc,deleted_loc,committer,comment,function_calls\n")

for fileKey in javaFiles:
    for i in range(8):
        javaFiles[fileKey][i] = javaFiles[fileKey][i] / javaFileCounts[fileKey]

    if fileKey in fileMetrics:
        file.write(fileKey)
            
        for i in range(8):
            file.write("," + str(javaFiles[fileKey][i]))

        for i in range(6):
            file.write("," + fileMetrics[fileKey][i])

file.close()

#-----------------------------------------------------

file = open("reports/joda-time/method-scores.csv", 'r')

javaFiles = dict()

javaFileCounts = dict()

for line in file:
    fileKey = line[0:line.find("(")]
        
    fileKey = fileKey.split("/")[-1]
    
    fileKey = fileKey.split(".")[0]

    fileKey = fileKey.replace(";","")
    
    if '$' in fileKey:
        fileKey = fileKey.split("$")[0]

    scores = line.split(",")[1:]

    if fileKey in javaFiles:
        for i in range(8):
            javaFiles[fileKey][i] = javaFiles[fileKey][i] + float(scores[i])

        javaFileCounts[fileKey] = javaFileCounts[fileKey] + 1

    else:
        
        javaFiles[fileKey] = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0]
        
        for i in range(8):
            javaFiles[fileKey][i] = float(scores[i])
        
        javaFileCounts[fileKey] = 1

file.close()

metricsFile = open("reports/joda-time/fin_joda", 'r')
fileMetrics = dict()

for line in metricsFile:
    fileName = line.split(" ")[0]
    fileName = fileName.split("/")[-1]
    fileName = fileName.split(".")[0]
    fileMetrics[fileName] = line.split(" ")[1:]

metricsFile.close()

file = open("./file-scores-joda-time.csv", 'w')

file.write("file_name,Tarantula,Tarantula_Weighted,Sbi,Sbi_Weighted,Jaccard,Jaccard_Weighted,Ochiai,Ochiai_Weighted,total_loc,churned_loc,deleted_loc,committer,comment,function_calls\n")

for fileKey in javaFiles:
    for i in range(8):
        javaFiles[fileKey][i] = javaFiles[fileKey][i] / javaFileCounts[fileKey]

    if fileKey in fileMetrics:
        file.write(fileKey)
            
        for i in range(8):
            file.write("," + str(javaFiles[fileKey][i]))

        for i in range(6):
            file.write("," + fileMetrics[fileKey][i])

file.close()
