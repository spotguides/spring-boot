{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "spotguide-spring-boot.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "spotguide-spring-boot.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "spotguide-spring-boot.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "call-nested" }}
{{- $dot := index . 0 }}
{{- $subchart := index . 1 }}
{{- $template := index . 2 }}
{{- include $template (dict "Chart" (dict "Name" $subchart) "Values" (index $dot.Values $subchart) "Release" $dot.Release "Capabilities" $dot.Capabilities) }}
{{- end }}

{{- define "repo-tag" }}
{{- if .Values.banzaicloud.organization.name }}
{{- range .Values.banzaicloud.tags }}
{{- if regexMatch "^repo:" . }}
{{- . }}
{{- end }}
{{- end }}
{{- end }}
{{- end }}

{{- define "repo-user" }}
{{- if .Values.banzaicloud.organization.name }}
{{- range .Values.banzaicloud.tags }}
{{- if regexMatch "^repo:" . }}
{{- $repoFullName := regexReplaceAll "^repo:" . "" }}
{{- first (splitList "/" $repoFullName) }}
{{- end }}
{{- end }}
{{- end }}
{{- end }}

{{- define "repo-name" }}
{{- if .Values.banzaicloud.organization.name }}
{{- range .Values.banzaicloud.tags }}
{{- if regexMatch "^repo:" . }}
{{- $repoFullName := regexReplaceAll "^repo:" . "" }}
{{- last (splitList "/" $repoFullName) }}
{{- end }}
{{- end }}
{{- end }}
{{- end }}
