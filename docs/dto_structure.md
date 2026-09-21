# Structure des DTO

## Vue d'ensemble

Les DTO (*Data Transfer Objects*) permettent de transporter les données entre les différentes couches de l'application sans exposer directement les entités JPA.

Pour gérer les exercices et les séances d'entraînement, on utilise quatre DTO :

* `WorkoutSessionDto`
* `ExerciseDto`
* `ExerciseWorkoutDto`
* `WorkoutSetDto`

La structure d'une séance est hiérarchique :

```text
WorkoutSessionDto
│
├── id
├── nom
├── date
├── notes
├── userId
│
└── exercises
      │
      ├── ExerciseWorkoutDto
      │     ├── exerciseId
      │     ├── exerciseName
      │     │
      │     └── sets
      │           │
      │           ├── WorkoutSetDto
      │           │     ├── setId
      │           │     ├── weight
      │           │     ├── reps
      │           │     └── orderIndex
      │           │
      │           ├── WorkoutSetDto
      │           └── WorkoutSetDto
      │
      └── ExerciseWorkoutDto
            ├── exerciseId
            ├── exerciseName
            │
            └── sets
                  ├── WorkoutSetDto
                  └── WorkoutSetDto
```

`ExerciseDto` n'apparaît pas directement dans cette hiérarchie car il représente un exercice **indépendamment d'une séance**.

---

# 1. WorkoutSessionDto

## Rôle

`WorkoutSessionDto` représente **une séance d'entraînement complète**.

C'est le DTO principal utilisé lorsqu'on souhaite afficher ou transmettre une séance.

Il contient les informations générales de la séance ainsi que la liste des exercices réalisés.

```java
public record WorkoutSessionDto(
        Long id,
        String nom,
        LocalDate date,
        String notes,
        Long userId,
        List<ExerciseWorkoutDto> exercises
) {}
```

## Attributs

| Attribut    | Type                       | Description                                    |
| ----------- | -------------------------- | ---------------------------------------------- |
| `id`        | `Long`                     | Identifiant de la séance                       |
| `nom`       | `String`                   | Nom de la séance                               |
| `date`      | `LocalDate`                | Date de la séance                              |
| `notes`     | `String`                   | Notes associées à la séance                    |
| `userId`    | `Long`                     | Identifiant de l'utilisateur propriétaire      |
| `exercises` | `List<ExerciseWorkoutDto>` | Liste des exercices réalisés pendant la séance |

## Exemple

```text
WorkoutSessionDto
│
├── id : 15
├── nom : "Push Day"
├── date : 2026-09-18
├── notes : "Bonne séance"
├── userId : 2
│
└── exercises
```

---

# 2. ExerciseDto

## Rôle

`ExerciseDto` représente **un exercice de manière générale**, indépendamment d'une séance.

Il peut être utilisé lorsqu'on récupère la liste des exercices disponibles dans l'application.

Par exemple, il peut servir à alimenter le `<select>` du formulaire de création d'une séance.

```java
public record ExerciseDto(
        Long id,
        String name
) {}
```

## Attributs

| Attribut | Type     | Description               |
| -------- | -------- | ------------------------- |
| `id`     | `Long`   | Identifiant de l'exercice |
| `name`   | `String` | Nom de l'exercice         |

## Exemple

La base de données peut contenir :

```text
ExerciseDto
├── id : 1
└── name : "Développé couché"

ExerciseDto
├── id : 2
└── name : "Squat"

ExerciseDto
├── id : 3
└── name : "Tractions"
```

Dans le formulaire de création d'une séance, ces DTO peuvent servir à afficher :

```text
[ -- Choisir un exercice -- ▼ ]

    Développé couché
    Squat
    Tractions
```

## Différence avec ExerciseWorkoutDto

`ExerciseDto` représente **l'exercice en lui-même**.

`ExerciseWorkoutDto` représente **un exercice réalisé dans une séance précise**, avec les séries effectuées.

```text
ExerciseDto

"Développé couché"
       │
       │ exercice disponible
       ↓
ExerciseWorkoutDto

"Développé couché"
       │
       ├── Série 1 : 80kg × 10
       ├── Série 2 : 80kg × 8
       └── Série 3 : 75kg × 10
```

---

# 3. ExerciseWorkoutDto

## Rôle

`ExerciseWorkoutDto` représente **un exercice réalisé dans une séance donnée**.

Il permet de faire le lien entre un exercice et les différentes séries réalisées pendant cette séance.

```java
public record ExerciseWorkoutDto(
        Long exerciseId,
        String exerciseName,
        List<WorkoutSetDto> sets
) {}
```

## Attributs

| Attribut       | Type                  | Description                                  |
| -------------- | --------------------- | -------------------------------------------- |
| `exerciseId`   | `Long`                | Identifiant de l'exercice                    |
| `exerciseName` | `String`              | Nom de l'exercice                            |
| `sets`         | `List<WorkoutSetDto>` | Liste des séries réalisées pour cet exercice |

## Exemple

```text
ExerciseWorkoutDto
│
├── exerciseId : 12
├── exerciseName : "Développé couché"
│
└── sets
      ├── Série 1
      ├── Série 2
      └── Série 3
```

Ce DTO est donc spécifique au **contexte d'une séance**.

Un même `ExerciseDto` peut être utilisé dans plusieurs séances, tandis que chaque séance peut avoir son propre `ExerciseWorkoutDto` avec des performances différentes.

---

# 4. WorkoutSetDto

## Rôle

`WorkoutSetDto` représente **une série effectuée pendant un exercice**.

Il contient les données nécessaires pour afficher les performances réalisées.

```java
public record WorkoutSetDto(
        Long setId,
        BigDecimal weight,
        Integer reps,
        Integer orderIndex
) {}
```

## Attributs

| Attribut     | Type         | Description             |
| ------------ | ------------ | ----------------------- |
| `setId`      | `Long`       | Identifiant de la série |
| `weight`     | `BigDecimal` | Poids utilisé           |
| `reps`       | `Integer`    | Nombre de répétitions   |
| `orderIndex` | `Integer`    | Ordre de la série       |

## Exemple

```text
WorkoutSetDto
├── setId : 42
├── weight : 80
├── reps : 10
└── orderIndex : 1
```

Cela signifie :

> Série numéro 1 : 80 kg pour 10 répétitions.

---

# Relation entre les DTO

Les DTO n'ont pas tous le même rôle.

```text
ExerciseDto
    │
    │ exercice générique
    │
    └──> utilisé notamment pour sélectionner
         un exercice dans le formulaire


WorkoutSessionDto
    │
    │ séance complète
    │
    └── List<ExerciseWorkoutDto>
              │
              │ exercice réalisé
              │
              └── List<WorkoutSetDto>
                        │
                        ├── série 1
                        ├── série 2
                        └── série 3
```

On peut donc résumer ainsi :

```text
ExerciseDto
    ↓
"Quel exercice existe dans l'application ?"


WorkoutSessionDto
    ↓
"Quelle est cette séance ?"


ExerciseWorkoutDto
    ↓
"Quels exercices ont été réalisés dans cette séance ?"


WorkoutSetDto
    ↓
"Quelles séries ont été réalisées pour cet exercice ?"
```

---

# Exemple concret

## Liste des exercices disponibles

```text
ExerciseDto

1 → Développé couché
2 → Squat
3 → Tractions
```

L'utilisateur crée ensuite une séance :

```text
Push Day
```

avec :

```text
Développé couché
    Série 1 → 80kg × 10
    Série 2 → 80kg × 8
    Série 3 → 75kg × 10

Élévations latérales
    Série 1 → 12kg × 15
    Série 2 → 12kg × 12
```

La séance sera représentée par :

```text
WorkoutSessionDto
│
├── id : 15
├── nom : "Push Day"
├── date : 2026-09-18
├── notes : "Bonne séance"
├── userId : 2
│
└── exercises
      │
      ├── ExerciseWorkoutDto
      │     ├── exerciseId : 1
      │     ├── exerciseName : "Développé couché"
      │     │
      │     └── sets
      │           ├── WorkoutSetDto
      │           │     ├── setId : 41
      │           │     ├── weight : 80
      │           │     ├── reps : 10
      │           │     └── orderIndex : 1
      │           │
      │           ├── WorkoutSetDto
      │           │     ├── setId : 42
      │           │     ├── weight : 80
      │           │     ├── reps : 8
      │           │     └── orderIndex : 2
      │           │
      │           └── WorkoutSetDto
      │                 ├── setId : 43
      │                 ├── weight : 75
      │                 ├── reps : 10
      │                 └── orderIndex : 3
      │
      └── ExerciseWorkoutDto
            ├── exerciseId : 18
            ├── exerciseName : "Élévations latérales"
            │
            └── sets
                  ├── WorkoutSetDto
                  │     ├── setId : 44
                  │     ├── weight : 12
                  │     ├── reps : 15
                  │     └── orderIndex : 1
                  │
                  └── WorkoutSetDto
                        ├── setId : 45
                        ├── weight : 12
                        ├── reps : 12
                        └── orderIndex : 2
```

---

# Les 4 DTO en une phrase

| DTO                  | Représente                                |
| -------------------- | ----------------------------------------- |
| `ExerciseDto`        | Un exercice disponible dans l'application |
| `WorkoutSessionDto`  | Une séance complète                       |
| `ExerciseWorkoutDto` | Un exercice réalisé dans une séance       |
| `WorkoutSetDto`      | Une série réalisée pour cet exercice      |

---

# Flux dans l'application

## Création d'une séance

```text
Base de données
      ↓
ExerciseDto
      ↓
Liste des exercices disponibles
      ↓
Formulaire HTML + JavaScript
      ↓
WorkoutSessionForm
      │
      └── List<WorkoutSetForm>
                ↓
             Entities
                ↓
             Base de données
```

## Affichage d'une séance

```text
Base de données
      ↓
Entities
      ↓
WorkoutSessionDto
      │
      └── List<ExerciseWorkoutDto>
                │
                └── List<WorkoutSetDto>
                        ↓
                    Thymeleaf
                        ↓
                 Page de détails
```

---

# Structure finale à retenir

```text
                    EXERCICE
                       │
                       │ ExerciseDto
                       ↓
              Exercice disponible
                       │
                       │
                       ↓
                SÉANCE CRÉÉE
                       │
                       │
              WorkoutSessionDto
                       │
                       └── exercises
                              │
                              ↓
                    ExerciseWorkoutDto
                              │
                              └── sets
                                    │
                                    ↓
                              WorkoutSetDto
                                    │
                                    ├── weight
                                    ├── reps
                                    └── orderIndex
```

L'objectif est que chaque DTO ait une **responsabilité claire** et ne mélange pas les informations des différentes parties de l'application.
