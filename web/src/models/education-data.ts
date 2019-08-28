export interface EducationData {
  userId: number;
  hide: boolean;
  id: number;
  nameOfInstitution: string;
  degree: string;
  location: string;
  startYear: Date;
  endYear: Date;
}

export function initEducationData() {
  return {
    userId: 0,
    hide: false,
    id: 0,
    nameOfInstitution: '',
    degree: '',
    location: '',
    startYear: null,
    endYear: null
  };
}


export interface ExperienceData {
  userId: number;
  hide: boolean;
  id: number;
  company: string;
  position: string;
  description: string;
  startYear: Date;
  endYear: Date;
}


export function initExperienceData() {
  return {
    userId: 0,
    hide: false,
    id: 0,
    company: '',
    position: '',
    description: '',
    startYear: null,
    endYear: null

  };

}

export interface Confirmation {
  title: string;
  message: string;
  yes: string;
  no: string;
}
